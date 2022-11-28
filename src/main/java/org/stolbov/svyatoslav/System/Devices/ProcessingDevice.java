package org.stolbov.svyatoslav.System.Devices;

import lombok.AllArgsConstructor;
import org.stolbov.svyatoslav.System.Dates.HomeRequest;

@AllArgsConstructor
public class ProcessingDevice {

    private int deviceNum;
    private HomeRequest homeRequestNow;
    private boolean status;

    public boolean getStatus() {
        return status;
    }

}
