package org.stolbov.svyatoslav.System;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.stolbov.svyatoslav.Statistics.StatisticController;
import org.stolbov.svyatoslav.System.Dates.Buffer;
import org.stolbov.svyatoslav.System.Managers.CompanySelectionManager;
import org.stolbov.svyatoslav.System.Managers.CompanyStagingManager;

@Getter
@Setter
public class GeneralSystem {

    private final StatisticController statisticController;
    private final int homeDeviceCount;
    private final int processingDeviceCount;
    private final Buffer buffer;
    private final CompanySelectionManager companySelectionManager;
    private final CompanyStagingManager companyStagingManager;

    public GeneralSystem(@NonNull int homeDeviceCount,
                         @NonNull int processingDeviceCount,
                         @NonNull int bufferSize) {
        this.statisticController = new StatisticController();
        this.homeDeviceCount = homeDeviceCount;
        this.processingDeviceCount = processingDeviceCount;
        this.buffer = new Buffer(bufferSize);
        this.companySelectionManager = new CompanySelectionManager(this.buffer, processingDeviceCount);
        this.companyStagingManager = new CompanyStagingManager(this.buffer, homeDeviceCount);
    }

}
