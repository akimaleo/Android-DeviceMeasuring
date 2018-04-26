package com.letoti.kawa.androiddevicemeasuring.data.imu;

public interface ImuUtil {
    /**
     * Starts listening sensors
     */
    void registerListeners();

    /**
     * Stops listening sensors
     */
    void unregisterListeners();

    /**
     * Subscribing for update
     *
     * @param onSensorsUpdateListener
     * @see com.letoti.kawa.androiddevicemeasuring.domain.entity.ImuData
     */
    void setOnSensorsUpdateListener(OnSensorsUpdateListener onSensorsUpdateListener);
}
