package org.stolbov.svyatoslav.Statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class HomeDeviceStatistic {

    private long countAllTasks;
    private long countCanceledTasks;
    private long totalTime;
    private long bufferTime;

    public HomeDeviceStatistic() {
        this.bufferTime = 0;
        this.totalTime = 0;
        this.countCanceledTasks = 0;
        this.countAllTasks = 0;
    }

    public void addCountAllTasks() {
        this.countAllTasks++;
    }

    public void addCountCanceledTasks() {
        this.countCanceledTasks++;
    }

    public void addTotalTime(@NonNull double time) {
        this.totalTime += time;
    }

    public void addBufferTime(@NonNull double time) {
        this.bufferTime += time;
    }

}
