package org.stolbov.svyatoslav.Statistics;

import lombok.NonNull;

public class StatisticController {

    private final int homeDeviceCount;
    private final int processingDeviceCount;
    private final int sizeOfBuffer;

    public StatisticController(@NonNull int homeDeviceCount,
                               @NonNull int processingDeviceCount,
                               @NonNull int sizeOfBuffer) {
        this.homeDeviceCount = homeDeviceCount;
        this.processingDeviceCount = processingDeviceCount;
        this.sizeOfBuffer = sizeOfBuffer;
    }

}
