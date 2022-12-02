package org.stolbov.svyatoslav.System.Managers;

import lombok.NonNull;
import org.stolbov.svyatoslav.System.Dates.Buffer;
import org.stolbov.svyatoslav.System.Devices.HomeDevice;

import java.util.ArrayList;

public class CompanyStagingManager {

    private final Buffer buffer;
    private final int homeDeviceCount;
    private ArrayList<HomeDevice> homeDevices;

    public CompanyStagingManager(Buffer buffer,
                                 int homeDeviceCount,
                                 double lambda) {
        this.buffer = buffer;
        this.homeDeviceCount = homeDeviceCount;
        initArrayOfDevice(lambda);
    }

    private void initArrayOfDevice(double lambda) {
        homeDevices = new ArrayList<>(homeDeviceCount);
        for (int i = 0; i < homeDeviceCount; i++) {
            homeDevices.add(new HomeDevice(i, lambda));
        }
    }

    public ArrayList<HomeDevice> getHomeDevices() {
        return homeDevices;
    }

    public HomeDevice getHomeDevice(int i) {
        return this.homeDevices.get(i);
    }

}
