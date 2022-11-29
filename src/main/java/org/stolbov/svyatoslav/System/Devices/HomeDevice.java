package org.stolbov.svyatoslav.System.Devices;

import lombok.Getter;
import lombok.Setter;
import org.stolbov.svyatoslav.System.Dates.HomeRequest;

@Getter
@Setter
public class HomeDevice {

    private final long sourceNum;
    private final double lambda;
    private long countGeneratedHomeRequest;
    private double timeNextHomeRequest;

    public HomeDevice(int sourceNum, int lambda) {
        this.sourceNum = sourceNum;
        this.lambda = lambda;
        this.countGeneratedHomeRequest = 0;
        this.timeNextHomeRequest = getLaplassNumber(this.lambda);
    }

    public HomeRequest getNewHomeRequest(double time) {
        this.countGeneratedHomeRequest++;
        return new HomeRequest(this.sourceNum, this.countGeneratedHomeRequest, time, 0, 0);
    }

    public double getTimeNextHomeRequest() {
        double temp = this.timeNextHomeRequest;
        timeNextHomeRequest += getLaplassNumber(this.lambda);
        return temp;
    }

    private double getLaplassNumber(double lambda) {
        double temp = 0.0;
        while (temp == 0.0) {
            temp = Math.random();
        }
        return -1.0/lambda * Math.log(temp);
    }
}
