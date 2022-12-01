package org.stolbov.svyatoslav.System;

import lombok.Getter;
import lombok.Setter;
import org.stolbov.svyatoslav.Statistics.StatisticController;
import org.stolbov.svyatoslav.System.Dates.Buffer;
import org.stolbov.svyatoslav.System.Dates.HomeRequest;
import org.stolbov.svyatoslav.System.Devices.ProcessingDevice;
import org.stolbov.svyatoslav.System.Managers.CompanySelectionManager;
import org.stolbov.svyatoslav.System.Managers.CompanyStagingManager;
import org.stolbov.svyatoslav.Utils.Action;
import org.stolbov.svyatoslav.Utils.ActionType;

import java.util.ArrayList;
@Getter
@Setter
public class GeneralSystem {

    private final StatisticController statisticController;
    private int homeDeviceCount;
    private int processingDeviceCount;
    private double timeNow;
    private Buffer buffer;
    private CompanySelectionManager companySelectionManager;
    private CompanyStagingManager companyStagingManager;
    private ArrayList<Action> actions;
    private double lambda;
    private int minTime;
    private int maxTime;
    private int bufferSize;
    private int currentProcessingDevicePosition;

    public GeneralSystem(int homeDeviceCount,
                         int processingDeviceCount,
                         int bufferSize,
                         double lambda,
                         int maxTime,
                         int minTime,
                         int countRequiredRequest) {
        this.statisticController = new StatisticController(homeDeviceCount, processingDeviceCount, bufferSize, countRequiredRequest,220000);
        this.homeDeviceCount = homeDeviceCount;
        this.processingDeviceCount = processingDeviceCount;
        this.lambda = lambda;
        this.maxTime = maxTime;
        this.minTime = minTime;
        this.timeNow = 0;
        this.currentProcessingDevicePosition = -1;
        this.bufferSize = bufferSize;
        this.buffer = new Buffer(bufferSize);
        this.companySelectionManager = new CompanySelectionManager(this.buffer, processingDeviceCount, this.minTime, this.maxTime);
        this.companyStagingManager = new CompanyStagingManager(this.buffer, homeDeviceCount, this.lambda);
        this.actions = new ArrayList<>(homeDeviceCount);
        for (int i = 0; i < homeDeviceCount; i++) {
            actions.add(new Action(ActionType.NEW_REQUEST,
                        companyStagingManager.getHomeDevice(i).getTimeNextHomeRequest(), i));
        }
        actions.sort(Action::compareTo);
    }

    public void execute() {
        while (!actions.isEmpty()) {
            startAction();
        }
    };

    public Action startAction() {
        Action action = actions.remove(0);
        this.timeNow = action.getActionTime();
        ActionType actionType = action.getActionType();
        int sourceOrDeviceNum = action.getSourceOrDeviceNum();
        if (actionType == ActionType.NEW_REQUEST) {
            if (statisticController.getCountSubmittedRequest() < statisticController.getCountRequiredRequest()) {
                buffer.addRequest(companyStagingManager.getHomeDevice(sourceOrDeviceNum).getNewHomeRequest(this.timeNow), this.statisticController);
                actions.add(new Action(ActionType.NEW_REQUEST, this.timeNow +
                        companyStagingManager.getHomeDevice(sourceOrDeviceNum).getTimeNextHomeRequest(), sourceOrDeviceNum));
                actions.add(new Action(ActionType.REQUEST_OUT_BUFFER, this.timeNow));
                statisticController.addHomeRequest(sourceOrDeviceNum);
                if (actions.size() > 0) {
                    actions.sort(Action::compareTo);
                }
            }
        }
        else if (actionType == ActionType.REQUEST_OUT_BUFFER) {
            int freeDeviceID = companySelectionManager.findFirstFreeProcessingDevice();
            if (freeDeviceID != -1 && !buffer.isEmpty()) {
                this.currentProcessingDevicePosition = freeDeviceID;
                ProcessingDevice currentProcessingDevice = companySelectionManager.getProcessingDevice(this.currentProcessingDevicePosition);
                companySelectionManager.getProcessingDevices().set(this.currentProcessingDevicePosition, null);
                HomeRequest homeRequest = buffer.getRequest();
                actions.add(new Action(ActionType.REQUEST_COMPLETE, this.timeNow + currentProcessingDevice.setHomeRequest(homeRequest, this.timeNow), currentProcessingDevice.getDeviceNum()));
                actions.sort(Action::compareTo);
                companySelectionManager.getProcessingDevices().set(this.currentProcessingDevicePosition, currentProcessingDevice);
            }
        }
        else if (actionType == ActionType.REQUEST_COMPLETE) {
            ProcessingDevice currentProcessingDevice = companySelectionManager.getProcessingDevice(sourceOrDeviceNum);
            companySelectionManager.getProcessingDevices().set(sourceOrDeviceNum, null);
            statisticController.completeHomeRequest(currentProcessingDevice.getHomeRequestNow().getSourceNum(), sourceOrDeviceNum,
                    this.timeNow - currentProcessingDevice.getHomeRequestNow().getGeneratedTime(),
                    this.timeNow - currentProcessingDevice.getStartTimeHomeRequest());
            currentProcessingDevice.setHomeRequest(null, this.timeNow);
            companySelectionManager.getProcessingDevices().set(sourceOrDeviceNum, currentProcessingDevice);
            actions.add(new Action(ActionType.REQUEST_OUT_BUFFER, this.timeNow));
            actions.sort(Action::compareTo);
        }
        return action;
    };

}
