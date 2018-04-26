package com.letoti.kawa.androiddevicemeasuring.presentation.features.table;

public class RecordingData {

    private Long startRecordingMillis;
    private Long lastRecordingMillis;

    private int updateCount;
    private boolean isRecording;

    public RecordingData() {
        this.startRecordingMillis = 0L;
        this.lastRecordingMillis = 0L;
        updateCount = 0;
        isRecording = false;
    }

    public void startRecording(Long startRecordingTime) {
        isRecording = true;
        startRecordingMillis = startRecordingTime;
        lastRecordingMillis = startRecordingTime;
        updateCount = 0;
    }

    public void stopRecording() {
        isRecording = false;
//        i need to clear data
//        startRecordingMillis = 0L;
//        lastRecordingMillis = 0L;
//        updateCount = 0;
    }

    public void updateDuration(Long currentRecordingTime) {
        if (!isRecording) {
            return;
        }
        updateCount++;
        this.lastRecordingMillis = currentRecordingTime;
    }

    public Long getRecordingDuration() {
        return lastRecordingMillis - startRecordingMillis;
    }

    public Integer getMinutes() {
        return ((int) (getRecordingDuration() / 1000 / 60));
    }

    public Integer getSeconds() {
        return ((int) (getRecordingDuration() / 1000) % 60);
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public boolean isRecording() {
        return isRecording;
    }
}