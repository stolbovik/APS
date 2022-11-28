package org.stolbov.svyatoslav.System.Managers;

import lombok.Getter;
import lombok.NonNull;
import org.stolbov.svyatoslav.System.Dates.Buffer;
import org.stolbov.svyatoslav.System.Devices.HomeDevice;
import org.stolbov.svyatoslav.System.Devices.ProcessingDevice;

import java.util.ArrayList;

public class CompanyStagingManager {

    private final Buffer buffer;
    private final int homeDeviceCount;
    private ArrayList<HomeDevice> homeDevices;

    public CompanyStagingManager(@NonNull Buffer buffer,
                                 @NonNull int homeDeviceCount) {
        this.buffer = buffer;
        this.homeDeviceCount = homeDeviceCount;
    }

    private void initArrayOfDevice() {
        homeDevices = new ArrayList<HomeDevice>(homeDeviceCount);
        for (int i = 1; i <= homeDeviceCount; i++) {
            homeDevices.add(new HomeDevice(i, 0));
        }
    }

}
