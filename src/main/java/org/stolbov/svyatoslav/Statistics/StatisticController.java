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
    private double totalTime;
    private ArrayList<HomeDeviceStatistic> homeDeviceStatistics;
    private ArrayList<ProcessingDeviceStatistic> processingDeviceStatistics;

    public StatisticController(int homeDeviceCount,
                               int processingDeviceCount,
                               int sizeOfBuffer,
                               int countRequiredRequest,
                               double totalTime) {
        this.homeDeviceCount = homeDeviceCount;
        this.processingDeviceCount = processingDeviceCount;
        this.sizeOfBuffer = sizeOfBuffer;
        this.countRequiredRequest = countRequiredRequest;
        this.countSubmittedRequest = 0;
        this.countCompletedRequest = 0;
        this.totalTime = totalTime;
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

    public void cancelHomeRequest(int homeDeviceNum, double timeOfUse) {
        this.homeDeviceStatistics.get(homeDeviceNum).addCountCanceledHomeRequests();
        this.completeHomeRequest(homeDeviceNum, -1, timeOfUse, 0);
    };

    public void completeHomeRequest(int homeDeviceNum, int processingDeviceNum, double usedTime, double timeOfProcessing)
    {
        if (homeDeviceNum == -1) {
            this.processingDeviceStatistics.get(processingDeviceNum).addWorkTime(timeOfProcessing);
        }
        this.homeDeviceStatistics.get(homeDeviceNum).addTotalTime(usedTime);
        this.homeDeviceStatistics.get(homeDeviceNum).addBufferTime(usedTime - timeOfProcessing);
        this.countCompletedRequest++;
    };

}
