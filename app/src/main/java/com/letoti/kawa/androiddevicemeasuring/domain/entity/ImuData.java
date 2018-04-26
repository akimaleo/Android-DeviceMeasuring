package com.letoti.kawa.androiddevicemeasuring.domain.entity;

public class ImuData {

    private Float azimuthAngle;
    private Float rollAngle;
    private Float pitchAngle;

    public ImuData() {
    }

    public ImuData(Float azimuthAngle, Float rollAngle, Float pitchAngle) {
        this.azimuthAngle = azimuthAngle;
        this.rollAngle = rollAngle;
        this.pitchAngle = pitchAngle;
    }

    public Float getAzimuthAngle() {
        return azimuthAngle;
    }

    public void setAzimuthAngle(Float azimuthAngle) {
        this.azimuthAngle = azimuthAngle;
    }

    public Float getRollAngle() {
        return rollAngle;
    }

    public void setRollAngle(Float rollAngle) {
        this.rollAngle = rollAngle;
    }

    public Float getPitchAngle() {
        return pitchAngle;
    }

    public void setPitchAngle(Float pitchAngle) {
        this.pitchAngle = pitchAngle;
    }

    public String[] toCsv() {
        return new String[]{getAzimuthAngle() + "",
                getRollAngle() + "",
                getPitchAngle() + ""};
    }
}
