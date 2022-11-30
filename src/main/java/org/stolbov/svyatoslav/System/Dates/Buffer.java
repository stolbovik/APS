package org.stolbov.svyatoslav.System.Dates;

import lombok.Getter;
import org.stolbov.svyatoslav.Statistics.StatisticController;

import java.util.ArrayList;
import java.util.Optional;

@Getter
public class Buffer {

    private final ArrayList<HomeRequest> buffer;
    final private int sizeBuffer;
    private int oldestRequestIndex;
    private int firstFreeIndex;
    private int lastRequestIndex;

    public Buffer(int size) {
        this.sizeBuffer = size;
        buffer = new ArrayList<>(size);
        for (int i = 0; i < sizeBuffer; i++) {
            buffer.add(null);
        }
        firstFreeIndex = 0;
        oldestRequestIndex = -1;
        lastRequestIndex = -1;
    }

    public boolean isEmpty() {
        return lastRequestIndex == -1;
    }

    public void addRequest(HomeRequest homeRequest,
                           StatisticController statisticController) {
        if (firstFreeIndex == -1) {
            HomeRequest homeRequest1 = buffer.get(oldestRequestIndex);
            statisticController.cancelHomeRequest(homeRequest1.getSourceNum(), homeRequest.getGeneratedTime() - homeRequest1.getGeneratedTime());
            buffer.set(oldestRequestIndex, homeRequest);
            lastRequestIndex = oldestRequestIndex;
            oldestRequestIndex = getNewOldestRequestIndex();
            return;
        }
        buffer.set(firstFreeIndex, homeRequest);
        lastRequestIndex = firstFreeIndex;
        oldestRequestIndex = getNewOldestRequestIndex();
        firstFreeIndex = getNewFirstFreeIndex();
    }

    public HomeRequest getRequest() {
        if (isEmpty()) {
            throw new RuntimeException("Buffer is empty. Can not receive task");
        }
        HomeRequest answer = buffer.get(lastRequestIndex);
        buffer.set(lastRequestIndex, null);
        lastRequestIndex = getNewLastRequestIndex();
        firstFreeIndex = getNewFirstFreeIndex();
        oldestRequestIndex = getNewOldestRequestIndex();
        return answer;
    }

    private int getNewOldestRequestIndex() {
        if (isEmpty()) {
            return -1;
        }
        int answerIndex = -1;
        double temp = -1.0;
        for (int i = 0; i < sizeBuffer; i++) {
            if (buffer.get(i) != null && (temp == -1.0 || temp > buffer.get(i).getGeneratedTime())) {
                temp = buffer.get(i).getGeneratedTime();
                answerIndex = i;
            }
        }
        return answerIndex;
    }

    private int getNewFirstFreeIndex() {
        for (int i = 0; i < sizeBuffer; i++) {
            if (buffer.get(i) == null) {
                return i;
            }
        }
        return -1;
    }

    private int getNewLastRequestIndex() {
        int answerIndex = -1;
        double temp = -1.0;
        for (int i = 0; i < sizeBuffer; i++) {
            if (buffer.get(i) != null && (temp == -1.0 || temp < buffer.get(i).getGeneratedTime())) {
                temp = buffer.get(i).getGeneratedTime();
                answerIndex = i;
            }
        }
        return answerIndex;
    }
}
