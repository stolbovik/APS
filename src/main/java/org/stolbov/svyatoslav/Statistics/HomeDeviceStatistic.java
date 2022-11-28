package org.stolbov.svyatoslav.Statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HomeDeviceStatistic {

    private long countAllTasks;
    private long countCompleteTasks;
    private long totalTime;
    private long bufferTime;

    public void addCountAllTasks() {
        this.countAllTasks++;
    }

    public void addCountCompleteTasks() {
        this.countCompleteTasks++;
    }

    public void addTotalTime(@NonNull double time) {
        this.totalTime += time;
    }

    public void addBufferTime(@NonNull double time) {
        this.bufferTime += time;
    }

}
