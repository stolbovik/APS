package org.stolbov.svyatoslav.Statistics;

import lombok.Getter;

@Getter
public class HomeDeviceStatistic {

    private long countAllGeneratedHomeRequests;
    private long countAllCanceledHomeRequests;
    private double timeInSystem;
    private double timeInDevice;
    private double timeInBuffer;

    public HomeDeviceStatistic() {
        this.timeInBuffer = 0;
        this.timeInSystem = 0;
        this.timeInDevice = 0;
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
        this.timeInSystem += time;
    }

    public void addBufferTime(double time) {
        this.timeInBuffer += time;
    }
    public void addTimeInDevice(double time) {
        this.timeInDevice += time;
    }

    public double getBufferTimeDispersion() { return (this.timeInBuffer * this.timeInBuffer /
            this.countAllGeneratedHomeRequests - Math.pow(this.timeInBuffer / this.countAllGeneratedHomeRequests, 2)); }
    public double getDeviceTimeDispersion() { return (this.timeInDevice * this.timeInDevice / this.countAllGeneratedHomeRequests
            - Math.pow(this.timeInDevice / this.countAllGeneratedHomeRequests, 2)); }

}
