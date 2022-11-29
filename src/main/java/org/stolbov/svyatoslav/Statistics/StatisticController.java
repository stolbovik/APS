package org.stolbov.svyatoslav.Statistics;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class StatisticController {

    private final int homeDeviceCount;
    private final int processingDeviceCount;
    private final int sizeOfBuffer;
    private int countRequiredRequest;
    private int countSubmittedRequest;
    private int countCompletedRequest;
    private double totalTime;
    private long countTotalTasks;
    private final ArrayList<HomeDeviceStatistic> homeDeviceStatistics;
    private final ArrayList<ProcessingDeviceStatistic> processingDeviceStatistics;

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
        this.countTotalTasks = 0;
        this.totalTime = totalTime;
        this.homeDeviceStatistics = new ArrayList<>(homeDeviceCount);
        for (int i = 0; i < homeDeviceCount; i++) {
            this.homeDeviceStatistics.add(i, new HomeDeviceStatistic());
        }
        this.processingDeviceStatistics = new ArrayList<>(processingDeviceCount);
        for (int i = 0; i < processingDeviceCount; i++) {
            this.processingDeviceStatistics.add(i, new ProcessingDeviceStatistic());
        }
    }

    public void addNewTask(int homeDeviceNum) {
        this.homeDeviceStatistics.get(homeDeviceNum).addCountAllTasks();
        this.countSubmittedRequest++;
    };

    public void cancelTask(int homeDeviceNum, double timeOfUse) {
        this.homeDeviceStatistics.get(homeDeviceNum).addCountCanceledTasks();
        this.taskCompleted(homeDeviceNum, -1, timeOfUse, 0);
    };

    public void taskCompleted(int homeDeviceNum, int processingDeviceNum, double usedTime, double processedTime)
    {
        if (homeDeviceNum != -1) {
            this.processingDeviceStatistics.get(processingDeviceNum).addWorkTime(processedTime);
        }
        this.homeDeviceStatistics.get(homeDeviceNum).addTotalTime(usedTime);
        this.homeDeviceStatistics.get(homeDeviceNum).addBufferTime(usedTime - processedTime);
        this.countCompletedRequest++;
    };

}
