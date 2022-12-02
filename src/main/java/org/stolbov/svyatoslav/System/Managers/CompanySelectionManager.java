package org.stolbov.svyatoslav.System.Managers;

import org.stolbov.svyatoslav.System.Dates.Buffer;
import org.stolbov.svyatoslav.System.Devices.ProcessingDevice;

import java.util.ArrayList;

public class CompanySelectionManager {

    private final Buffer buffer;
    private final int processingDeviceCount;
    private ArrayList<ProcessingDevice> processingDevices;

    public CompanySelectionManager(Buffer buffer,
                                   int processingDeviceCount,
                                   int minTime,
                                   int maxTime) {
        this.buffer = buffer;
        this.processingDeviceCount = processingDeviceCount;
        initArrayOfDevice(minTime, maxTime);
    }

    private void initArrayOfDevice(int minTime, int maxTime) {
        processingDevices = new ArrayList<>(processingDeviceCount);
        for (int i = 0; i < processingDeviceCount; i++) {
            processingDevices.add(new ProcessingDevice(i, minTime,maxTime));
        }
    }

    public ArrayList<ProcessingDevice> getProcessingDevices() {
        return processingDevices;
    }

    public ProcessingDevice getProcessingDevice(int i) {
        return this.processingDevices.get(i);
    }

    public int findFirstFreeProcessingDevice() {
        for (int i = 0; i < processingDeviceCount; i++) {
            if (this.getProcessingDevice(i).isFree()) {
                return this.getProcessingDevice(i).getDeviceNum();
            }
        }
        return -1;
    };

}
