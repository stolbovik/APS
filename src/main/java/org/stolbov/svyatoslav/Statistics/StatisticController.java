package org.stolbov.svyatoslav.Statistics;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public class StatisticController {

    private int processingDeviceCount;
    private int homeDeviceCount;
    private int countRequiredRequest;
    private int sizeOfBuffer;
    private int countSubmittedRequest;
    private int countCompletedRequest;
/*    private double totalTime;*/
    private ArrayList<HomeDeviceStatistic> homeDeviceStatistics;
    private ArrayList<ProcessingDeviceStatistic> processingDeviceStatistics;

    public StatisticController(int homeDeviceCount,
                               int processingDeviceCount,
                               int sizeOfBuffer,
                               int countRequiredRequest) {
        this.homeDeviceCount = homeDeviceCount;
        this.processingDeviceCount = processingDeviceCount;
        this.sizeOfBuffer = sizeOfBuffer;
        this.countRequiredRequest = countRequiredRequest;
        this.countSubmittedRequest = 0;
        this.countCompletedRequest = 0;
        this.homeDeviceStatistics = new ArrayList<>(homeDeviceCount);
        for (int i = 0; i < homeDeviceCount; i++) {
            this.homeDeviceStatistics.add(new HomeDeviceStatistic());
        }
        this.processingDeviceStatistics = new ArrayList<>(processingDeviceCount);
        for (int i = 0; i < processingDeviceCount; i++) {
            this.processingDeviceStatistics.add(new ProcessingDeviceStatistic());
        }
    }

    public void addHomeRequest(int homeDeviceNum) {
        this.homeDeviceStatistics.get(homeDeviceNum).addCountAllGeneratedHomeRequests();
        this.countSubmittedRequest++;
    };

    public void cancelHomeRequest(int homeDeviceNum, double timeInSystem) {
        this.homeDeviceStatistics.get(homeDeviceNum).addCountCanceledHomeRequests();
        this.completeHomeRequest(homeDeviceNum, 0, timeInSystem, 0.0);
    };

    public void completeHomeRequest(int homeDeviceNum, int processingDeviceNum, double timeInSystem, double timeInDevice)
    {
        this.processingDeviceStatistics.get(processingDeviceNum).addWorkTime(timeInDevice);
        this.homeDeviceStatistics.get(homeDeviceNum).addTotalTime(timeInSystem);
        this.homeDeviceStatistics.get(homeDeviceNum).addBufferTime(timeInSystem - timeInDevice);
        this.homeDeviceStatistics.get(homeDeviceNum).addTimeInDevice(timeInDevice);
        this.countCompletedRequest++;
    };

}
