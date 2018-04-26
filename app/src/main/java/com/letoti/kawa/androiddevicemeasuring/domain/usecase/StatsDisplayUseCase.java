package com.letoti.kawa.androiddevicemeasuring.domain.usecase;

public interface StatsDisplayUseCase {

    /**
     * Initializes and subscribes view for data update
     */
    void startDataFetching();

    /**
     * Stops data retrieving
     */
    void stopDataFetching();

    /**
     * Starts and Stops IMU data recording to file
     */
    void toggleRecording();
}
