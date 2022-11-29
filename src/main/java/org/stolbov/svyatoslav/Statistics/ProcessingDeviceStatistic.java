package org.stolbov.svyatoslav.Statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class ProcessingDeviceStatistic {

    private double workTime;

    public ProcessingDeviceStatistic() {
        this.workTime = 0;
    }

    public void addWorkTime(@NonNull double time) {
        workTime += time;
    }

}
