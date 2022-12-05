package org.stolbov.svyatoslav.System.Managers;

import org.stolbov.svyatoslav.System.Dates.Buffer;
import org.stolbov.svyatoslav.System.Dates.HomeRequest;
import org.stolbov.svyatoslav.System.Devices.ProcessingDevice;

import java.util.ArrayList;

public class CompanySelectionManager {

    private final Buffer buffer;
    private final int processingDeviceCount;
    private ArrayList<ProcessingDevice> processingDevices;

    public CompanySelectionManager(Buffer buffer,
                                   int processingDeviceCount,
                                   double minTime,
                                   double maxTime) {
        this.buffer = buffer;
        this.processingDeviceCount = processingDeviceCount;
        initArrayOfDevice(minTime, maxTime);
    }

    private void initArrayOfDevice(double minTime, double maxTime) {
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
    
    public HomeRequest getHomeRequest() {
        HomeRequest answer = buffer.getBuffer().get(buffer.getLastRequestIndex());
        buffer.getBuffer().set(buffer.getLastRequestIndex(), null);
        buffer.setNewLastRequestIndex();
        buffer.setNewFirstFreeIndex();
        buffer.setNewOldestRequestIndex();
        return answer;
    }

}
