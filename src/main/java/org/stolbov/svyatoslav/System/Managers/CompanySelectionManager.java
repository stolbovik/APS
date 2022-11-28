package org.stolbov.svyatoslav.System.Managers;

import lombok.NonNull;
import org.stolbov.svyatoslav.System.Dates.Buffer;
import org.stolbov.svyatoslav.System.Devices.ProcessingDevice;

import java.util.ArrayList;

public class CompanySelectionManager {

    private final Buffer buffer;
    private final int processingDeviceCount;
    private ArrayList<ProcessingDevice> processingDevices;

    public CompanySelectionManager(@NonNull Buffer buffer,
                                   @NonNull int processingDeviceCount) {
        this.buffer = buffer;
        this.processingDeviceCount = processingDeviceCount;
        initArrayOfDevice();
    }

    private void initArrayOfDevice() {
        processingDevices = new ArrayList<ProcessingDevice>(processingDeviceCount);
        for (int i = 1; i <= processingDeviceCount; i++) {
            processingDevices.add(new ProcessingDevice(i, null, false));
        }
    }

}
