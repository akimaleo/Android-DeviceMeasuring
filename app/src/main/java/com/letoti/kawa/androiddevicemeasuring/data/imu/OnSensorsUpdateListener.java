package com.letoti.kawa.androiddevicemeasuring.data.imu;

import com.letoti.kawa.androiddevicemeasuring.domain.entity.ImuData;

public interface OnSensorsUpdateListener {
    void onUpdate(ImuData data);
}
