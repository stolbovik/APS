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
    private double minTime;
    private double maxTime;
    private int bufferSize;

    public GeneralSystem(int homeDeviceCount,
                         int processingDeviceCount,
                         int bufferSize,
                         double lambda,
                         double maxTime,
                         double minTime,
                         int countRequiredRequest) {
        this.statisticController = new StatisticController(homeDeviceCount, processingDeviceCount, bufferSize, countRequiredRequest);
        this.homeDeviceCount = homeDeviceCount;
        this.processingDeviceCount = processingDeviceCount;
        this.lambda = lambda;
        this.maxTime = maxTime;
        this.minTime = minTime;
        this.timeNow = 0;
        this.bufferSize = bufferSize;
        this.buffer = new Buffer(bufferSize);
        this.companySelectionManager = new CompanySelectionManager(this.buffer, processingDeviceCount, this.minTime, this.maxTime);
        this.companyStagingManager = new CompanyStagingManager(this.buffer, homeDeviceCount, this.lambda, this.statisticController);
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

    public Action getNextAction() {
        return actions.get(0);
    }

    public Action startAction() {
        Action action = actions.remove(0);
        this.timeNow = action.getActionTime();
        ActionType actionType = action.getActionType();
        int sourceOrDeviceNum = action.getSourceOrDeviceNum();
        if (actionType == ActionType.NEW_REQUEST) {

            if (statisticController.getCountSubmittedRequest() < statisticController.getCountRequiredRequest()) {

                HomeRequest homeRequest = companyStagingManager.getHomeDevice(sourceOrDeviceNum).getNewHomeRequest(this.timeNow);

/*                System.out.println("\nType: " + action.getActionType() +
                        "\nTime: " + action.getActionTime() +
                        "\nSourceRequestNum: " + homeRequest.getHomeDeviceNum() + "." + homeRequest.getRequestNum() + "\n");*/

                companyStagingManager.addHomeRequestInBuffer(homeRequest);
                actions.add(new Action(ActionType.NEW_REQUEST, this.timeNow +
                        companyStagingManager.getHomeDevice(sourceOrDeviceNum).getTimeNextHomeRequest(), sourceOrDeviceNum));
                if (companySelectionManager.findFirstFreeProcessingDevice() != -1) {
                        actions.add(new Action(ActionType.REQUEST_OUT_BUFFER, this.timeNow));
                }
                statisticController.addHomeRequest(sourceOrDeviceNum);
                actions.sort(Action::compareTo);
            }
        }
        else if (actionType == ActionType.REQUEST_OUT_BUFFER) {
            int freeDeviceID = companySelectionManager.findFirstFreeProcessingDevice();
            if (!buffer.isEmpty()) {
                ProcessingDevice currentProcessingDevice = companySelectionManager.getProcessingDevice(freeDeviceID);

/*                System.out.println("\nType: " + action.getActionType() +
                        "\nTime: " + action.getActionTime() +
                        "\nFreeDevice: " + currentProcessingDevice.getDeviceNum() + "\n");*/

                companySelectionManager.getProcessingDevices().set(freeDeviceID, null);
                HomeRequest homeRequest = companySelectionManager.getHomeRequest();
                actions.add(new Action(ActionType.REQUEST_COMPLETE, this.timeNow + currentProcessingDevice.setHomeRequest(homeRequest, this.timeNow), currentProcessingDevice.getDeviceNum()));
                companySelectionManager.getProcessingDevices().set(freeDeviceID, currentProcessingDevice);
                actions.sort(Action::compareTo);
            }
        }
        else if (actionType == ActionType.REQUEST_COMPLETE) {
            ProcessingDevice currentProcessingDevice = companySelectionManager.getProcessingDevice(sourceOrDeviceNum);
/*
            System.out.println("\nType: " + action.getActionType() +
                    "\nTime: " + action.getActionTime() +
                    "\nDeviceAndRequest: " + currentProcessingDevice.getDeviceNum() + " "
                    + currentProcessingDevice.getHomeRequestNow().getHomeDeviceNum() + "." + currentProcessingDevice.getHomeRequestNow().getRequestNum() + "\n");*/

            companySelectionManager.getProcessingDevices().set(sourceOrDeviceNum, null);
            statisticController.completeHomeRequest(currentProcessingDevice.getHomeRequestNow().getHomeDeviceNum(), sourceOrDeviceNum,
                    this.timeNow - currentProcessingDevice.getHomeRequestNow().getGeneratedTime(),
                    this.timeNow - currentProcessingDevice.getStartTimeHomeRequest());
            currentProcessingDevice.setHomeRequest(null, 0);
            companySelectionManager.getProcessingDevices().set(sourceOrDeviceNum, currentProcessingDevice);
            actions.add(new Action(ActionType.REQUEST_OUT_BUFFER, this.timeNow));
            actions.sort(Action::compareTo);
        }
        return action;
    };

}
