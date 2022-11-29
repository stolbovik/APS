package org.stolbov.svyatoslav.System;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.stolbov.svyatoslav.Statistics.StatisticController;
import org.stolbov.svyatoslav.System.Dates.Buffer;
import org.stolbov.svyatoslav.System.Managers.CompanySelectionManager;
import org.stolbov.svyatoslav.System.Managers.CompanyStagingManager;
import org.stolbov.svyatoslav.Utils.Action;
import org.stolbov.svyatoslav.Utils.ActionType;
import org.stolbov.svyatoslav.amsha.Device;
import org.stolbov.svyatoslav.amsha.Task;

import java.util.ArrayList;
import java.util.Iterator;

@Getter
@Setter
public class GeneralSystem {

    private final StatisticController statisticController;
    private final int homeDeviceCount;
    private final int processingDeviceCount;
    private final Buffer buffer;
    private final CompanySelectionManager companySelectionManager;
    private final CompanyStagingManager companyStagingManager;
    private final ArrayList<Action> actions;
    private final double lambda;
    private final double min;
    private final double max;
    private double timeNow;
    private final int countRequiredRequest;

    public GeneralSystem(int homeDeviceCount,
                         int processingDeviceCount,
                         int bufferSize,
                         double lambda,
                         double max,
                         double min,
                         int countRequiredRequest) {
        this.statisticController = new StatisticController(homeDeviceCount, processingDeviceCount, bufferSize);
        this.homeDeviceCount = homeDeviceCount;
        this.processingDeviceCount = processingDeviceCount;
        this.lambda = lambda;
        this.max = max;
        this.min = min;
        this.timeNow = 0;
        this.countRequiredRequest = countRequiredRequest;
        this.buffer = new Buffer(bufferSize);
        this.companySelectionManager = new CompanySelectionManager(this.buffer, processingDeviceCount);
        this.companyStagingManager = new CompanyStagingManager(this.buffer, homeDeviceCount);
        this.actions = new ArrayList<>(homeDeviceCount);
        for (int i = 0; i < homeDeviceCount; i++) {
            actions.add(new Action(ActionType.NEW_REQUEST,
                        companyStagingManager.getHomeDevices().get(i).getTimeNextHomeRequest(), i));
        }
        actions.sort(Action::compareTo);
    }

    public void execute() {
        while (!actions.isEmpty()) {
            startAction();
        }
    };

    public Action startAction()
    {
        Action action = actions.remove(0);
        timeNow = action.getActionTime();
        ActionType actionType = action.getActionType();
        int sourceOrDeviceNum = action.getSourceOrDeviceNum();
        if (actionType == ActionType.NEW_REQUEST)
        {
            if (statisticController.getCountTotalTasks() < this.countRequiredRequest) {
                buffer.addRequest(companyStagingManager.getHomeDevices().get(sourceOrDeviceNum).getNewHomeRequest(timeNow), );
                events.add(new Event(Type.Generate, currentTime + sources.get((int) sourceId).nextTaskGenerationTime(), sourceId));
                events.add(new Event(Type.Unbuffer, currentTime));
                stat.taskGenerated((int) sourceId);
//                System.out.println("after: " + sourceId + " " + stat.getSources().get((int)sourceId).getCanceledTasksCount());
                if (events.size() > 0)
                {
                    events.sort(Event::compare);
                }
            }
        }
        else if (actionType == ActionType.REQUEST_OUT_BUFFER)
        {
//                    System.out.println("unbuffering");
            findPosition();
            if (devices.get((int) currentPosition).isFree())
            {
                Device currentDevice = devices.get((int) currentPosition);
                devices.set((int) currentPosition, null);
                if (buffer.isEmpty() || !currentDevice.isFree())
                {
                    devices.set((int) currentPosition, currentDevice);
                }
                else
                {
                    Task task = buffer.getNextTask();
                    events.add(new Event(Type.Complete, currentTime + currentDevice.setNextTask(task, currentTime), currentDevice.getDeviceId()));
                    if (events.size() > 0)
                    {
                        events.sort(Event::compare);
                    }
                    devices.set((int) currentPosition, currentDevice);
                    currentPosition++;
                    if (currentPosition == devicesCount)
                    {
                        currentPosition = 0;
                    }
                }
            }
        }
        else if (actionType == ActionType.TASK_COMPLETE)
        {
//                   System.out.println("completing task");
            Device currentDevice = null;
            Iterator<Device> it = devices.iterator();
            while (it.hasNext()) {
                Device temp = it.next();
                if (temp.getDeviceId() == sourceId) {
                    currentDevice = temp;
                    break;
                }
            }
            int pos = devices.indexOf(currentDevice);
            devices.set(pos, null);
            stat.taskCompleted(currentDevice.getCurrentTask().getSourceId(), (int) sourceId,
                    currentTime - currentDevice.getCurrentTask().getStartTime(),
                    currentTime - currentDevice.getTaskStartTime());
            currentDevice.setNextTask(null, currentTime);
            devices.set(pos, currentDevice);
            events.add(new Event(Type.Unbuffer, currentTime));
            if (events.size() > 0)
            {
                events.sort(Event::compare);
            }
        }
        return action;
    };

}
