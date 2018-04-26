package com.letoti.kawa.androiddevicemeasuring.presentation.features.table;

import android.content.Context;
import android.icu.util.TimeUnit;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.TimeUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.letoti.kawa.androiddevicemeasuring.databinding.ViewTableImuBinding;
import com.letoti.kawa.androiddevicemeasuring.domain.entity.ImuData;

/**
 * Displaying table with IMU data and support information
 * such as recording duration and number of updates
 */
public class ImuTableView extends FrameLayout {

    private ViewTableImuBinding mBinding;

    public ImuTableView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImuTableView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * Binding view and clearing values in table
     */
    private void init() {
        mBinding = ViewTableImuBinding.inflate(LayoutInflater.from(getContext()), this, true);
        clearData();
    }

    /**
     * Set IMU data for displaying
     *
     * @param data
     */
    public void setData(ImuData data) {
        mBinding.setData(data);
    }

    /**
     * Shows recording data information
     *
     * @param data
     */
    public void setRecordingData(RecordingData data) {
        mBinding.setRecordingData(data);
    }

    /**
     * Clearing values in table
     */
    public void clearData() {
        mBinding.setData(new ImuData());
        mBinding.setRecordingData(new RecordingData());
    }
}
