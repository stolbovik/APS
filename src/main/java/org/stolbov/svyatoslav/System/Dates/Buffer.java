package org.stolbov.svyatoslav.System.Dates;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Optional;

@Getter
public class Buffer {

    private final ArrayList<HomeRequest> buffer;
    final private int sizeBuffer;
    private int oldestRequestIndex;
    private int firstFreeIndex;
    private int lastRequestIndex;

    public Buffer(@NonNull final int size) {
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

    public Optional<HomeRequest> getRequest(@NonNull double unBufferTime) {
        if (isEmpty()) {
            return Optional.empty();
        }
        Optional<HomeRequest> answer = Optional.of(buffer.get(lastRequestIndex));
        buffer.set(lastRequestIndex, null);
        lastRequestIndex = getNewLastRequestIndex();
        firstFreeIndex = getNewFirstFreeIndex();
        oldestRequestIndex = getNewOldestRequestIndex();
        answer.ifPresent(homeRequest -> homeRequest.setUnBufferTime(unBufferTime));
        return answer;
    }

    public void addRequest(@NonNull HomeRequest homeRequest,
                           @NonNull double inBufferTime) {
        if (firstFreeIndex == -1) {
            buffer.set(oldestRequestIndex, homeRequest);
            lastRequestIndex = oldestRequestIndex;
            oldestRequestIndex = getNewOldestRequestIndex();
            homeRequest.setInBufferTime(inBufferTime);
            return;
        }
        buffer.set(firstFreeIndex, homeRequest);
        lastRequestIndex = firstFreeIndex;
        oldestRequestIndex = getNewOldestRequestIndex();
        firstFreeIndex = getNewFirstFreeIndex();
        homeRequest.setInBufferTime(inBufferTime);
    }

    private int getNewOldestRequestIndex() {
        int answerIndex = -1;
        double temp = -1;
        for (int i = 0; i < sizeBuffer; i++) {
            if (buffer.get(i) != null && (temp == -1 || temp >= buffer.get(i).getInBufferTime())) {
                temp = buffer.get(i).getInBufferTime();
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
            if (buffer.get(i) != null && (temp == -1 || temp < buffer.get(i).getInBufferTime())) {
                temp = buffer.get(i).getInBufferTime();
                answerIndex = i;
            }
        }
        return answerIndex;
    }
}
