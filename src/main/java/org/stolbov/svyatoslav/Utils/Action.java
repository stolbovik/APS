package org.stolbov.svyatoslav.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Action implements Comparable<Action>{

    private ActionType actionType;
    private double actionTime;
    private long sourceOrDeviceNum;

    public Action(@NonNull ActionType actionType,
                  @NonNull double actionTime) {
        this.actionTime = actionTime;
        this.actionType = actionType;
        this.sourceOrDeviceNum = 0;
    }

    @Override
    public int compareTo(Action temp) {
        if (this.actionTime > temp.getActionTime())  {
            return 1;
        }
        if (this.actionTime < temp.getActionTime()) {
            return -1;
        }
        if (this.getActionType().ordinal() > temp.getActionType().ordinal()) {
            return -1;
        }
        return 0;
    }
}
