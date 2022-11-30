package org.stolbov.svyatoslav.Statistics;

import lombok.Getter;

@Getter
public class HomeDeviceStatistic {

    private long countAllGeneratedHomeRequests;
    private long countAllCanceledHomeRequests;
    private double totalTime;
    private double totalBufferTime;

    public HomeDeviceStatistic() {
        this.totalBufferTime = 0;
        this.totalTime = 0;
        this.countAllCanceledHomeRequests = 0;
        this.countAllGeneratedHomeRequests = 0;
    }

    public void addCountAllGeneratedHomeRequests() {
        this.countAllGeneratedHomeRequests++;
    }

    public void addCountCanceledHomeRequests() {
        this.countAllCanceledHomeRequests++;
    }

    public void addTotalTime(double time) {
        this.totalTime += time;
    }

    public void addBufferTime(double time) {
        this.totalBufferTime += time;
    }

    public double getBufferTimeDispersion() { return (this.totalBufferTime * this.totalBufferTime /
            this.countAllGeneratedHomeRequests - Math.pow(this.totalBufferTime / this.countAllGeneratedHomeRequests, 2)); }
    public double getTotalTimeDispersion() { return (this.totalTime * this.totalTime / this.countAllGeneratedHomeRequests
            - Math.pow(this.totalTime / this.countAllGeneratedHomeRequests, 2)); }

}
