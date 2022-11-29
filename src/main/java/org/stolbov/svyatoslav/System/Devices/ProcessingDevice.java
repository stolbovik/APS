package org.stolbov.svyatoslav.System.Devices;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.stolbov.svyatoslav.System.Dates.HomeRequest;

@Setter
@Getter
public class ProcessingDevice {

    private long deviceNum;
    private HomeRequest homeRequestNow;
    private long minTime;
    private long maxTime;
    private double startTimeHomeRequest;

    public ProcessingDevice(@NonNull long deviceNum,
                            @NonNull long minTime,
                            @NonNull long maxTime) {
        this.deviceNum = deviceNum;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.homeRequestNow = null;
        this.startTimeHomeRequest = 0;
    }

    public double setHomeRequest(@NonNull HomeRequest homeRequest,
                                 double startTimeHomeRequest) {
        this.startTimeHomeRequest = startTimeHomeRequest;
        this.homeRequestNow = homeRequest;
        this.homeRequestNow.setUnBufferTime(startTimeHomeRequest);
        double temp = 0.0;
        while (temp == 0.0) {
            temp = Math.random();
        }
        return temp * this.minTime;
    }

    public boolean isFree() {
        return homeRequestNow == null;
    }

}
