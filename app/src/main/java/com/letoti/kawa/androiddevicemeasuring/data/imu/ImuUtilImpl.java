package com.letoti.kawa.androiddevicemeasuring.data.imu;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.letoti.kawa.androiddevicemeasuring.domain.entity.ImuData;

public class ImuUtilImpl implements SensorEventListener, ImuUtil {

    private SensorManager mSensorManager;
    private final float[] mAccelerometerReading = new float[3];
    private final float[] mMagnetometerReading = new float[3];

    private final float[] mRotationMatrix = new float[9];
    private final float[] mOrientationAngles = new float[3];
    private OnSensorsUpdateListener onSensorsUpdateListener;

    public ImuUtilImpl(SensorManager sensorManager) {
        this.mSensorManager = sensorManager;
    }

    public ImuUtilImpl(Context context) {
        this.mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void registerListeners() {
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_UI);
    }

    public void unregisterListeners() {
        mSensorManager.unregisterListener(this);
    }

    // Get readings from accelerometer and magnetometer. To simplify calculations,
    // consider storing these readings as unit vectors.
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, mAccelerometerReading,
                    0, mAccelerometerReading.length);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, mMagnetometerReading,
                    0, mMagnetometerReading.length);
        }
        compute();
    }

    // Compute the three orientation angles based on the most recent readings from
    // the device's accelerometer and magnetometer.
    private void compute() {
        // Update rotation matrix, which is needed to update orientation angles.
        mSensorManager.getRotationMatrix(mRotationMatrix, null,
                mAccelerometerReading, mMagnetometerReading);

//        float[] outGravity = new float[9];
//        SensorManager.remapCoordinateSystem(mRotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, outGravity);
        mSensorManager.getOrientation(mRotationMatrix, mOrientationAngles);

        float toDegrees = 57.2957795f;//radians to degrees (180/Math.PI)
        float azimuth = mOrientationAngles[0] * toDegrees;
        float pitch = mOrientationAngles[1] * toDegrees;
        float roll = mOrientationAngles[2] * toDegrees;

        if (onSensorsUpdateListener != null) {
            onSensorsUpdateListener.onUpdate(new ImuData(azimuth, roll, pitch));
        }
    }

    public void setOnSensorsUpdateListener(OnSensorsUpdateListener onSensorsUpdateListener) {
        this.onSensorsUpdateListener = onSensorsUpdateListener;
    }
}
