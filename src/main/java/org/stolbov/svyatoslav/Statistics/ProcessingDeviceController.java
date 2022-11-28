package org.stolbov.svyatoslav.Statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ProcessingDeviceController {

    private double workTime;

    public void addWorkTime(@NonNull double time) {
        workTime += time;
    }

}
