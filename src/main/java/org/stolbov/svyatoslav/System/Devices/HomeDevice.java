package org.stolbov.svyatoslav.System.Devices;

public class HomeDevice {

    private int sourceNum;
    private int numNextRequest;
    private int lambda;

    public HomeDevice(int sourceNum, int lambda) {
        this.sourceNum = sourceNum;
        this.lambda = lambda;
    }

}
