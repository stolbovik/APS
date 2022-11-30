package org.stolbov.svyatoslav.System.Devices;
import lombok.Getter;
import org.stolbov.svyatoslav.System.Dates.HomeRequest;

@Getter
public class ProcessingDevice {

    private int deviceNum;
    private HomeRequest homeRequestNow;
    private long minTime;
    private long maxTime;
    private double startTimeHomeRequest;

    public ProcessingDevice(int deviceNum,
                            long minTime,
                            long maxTime) {
        this.deviceNum = deviceNum;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.homeRequestNow = null;
        this.startTimeHomeRequest = 0;
    }

    public double setHomeRequest(HomeRequest homeRequest,
                                 double startTimeHomeRequest) {
        this.startTimeHomeRequest = startTimeHomeRequest;
        this.homeRequestNow = homeRequest;
        double temp = 0.0;
        while (temp == 0.0) {
            temp = Math.random();
        }
        return temp + this.minTime;
    }

    public boolean isFree() {
        return this.homeRequestNow == null;
    }

}
