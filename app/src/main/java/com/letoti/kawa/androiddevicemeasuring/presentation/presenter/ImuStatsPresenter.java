package com.letoti.kawa.androiddevicemeasuring.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.letoti.kawa.androiddevicemeasuring.R;
import com.letoti.kawa.androiddevicemeasuring.data.csv.CsvWorker;
import com.letoti.kawa.androiddevicemeasuring.data.imu.ImuUtil;
import com.letoti.kawa.androiddevicemeasuring.data.imu.OnSensorsUpdateListener;
import com.letoti.kawa.androiddevicemeasuring.domain.entity.ImuData;
import com.letoti.kawa.androiddevicemeasuring.domain.usecase.StatsDisplayUseCase;
import com.letoti.kawa.androiddevicemeasuring.presentation.features.table.RecordingData;
import com.letoti.kawa.androiddevicemeasuring.presentation.view.ImuStatsView;

import java.io.IOException;

@InjectViewState
public class ImuStatsPresenter extends MvpPresenter<ImuStatsView> implements StatsDisplayUseCase {

    private RecordingData mRecordDuration;
    private ImuUtil mImuUtil;
    private CsvWorker mCsvWorker;
    private long mTimeFromLastIteration;

    public ImuStatsPresenter() {
        mRecordDuration = new RecordingData();
    }

    public void setmImuUtil(ImuUtil mImuUtil) {
        this.mImuUtil = mImuUtil;
        mImuUtil.setOnSensorsUpdateListener(new OnSensorsUpdateListener() {
            @Override
            public void onUpdate(ImuData data) {
                onDataUpdate(data);
            }
        });
    }

    @Override
    public void startDataFetching() {
        mImuUtil.registerListeners();
    }

    @Override
    public void stopDataFetching() {
        mImuUtil.unregisterListeners();
    }

    @Override
    public void toggleRecording() {
        if (mRecordDuration.isRecording()) {
            stopRecording();
        } else {
            startRecording();
        }
    }

    /**
     * Starts recoding to file
     */
    private void startRecording() {
        mTimeFromLastIteration = System.currentTimeMillis();
        getViewState().startRecording();
        mRecordDuration.startRecording(System.currentTimeMillis());
        mCsvWorker = new CsvWorker();
        startWriteFile();
    }

    /**
     * Stops recoding to file
     */
    private void stopRecording() {
        getViewState().stopRecording();
        mRecordDuration.stopRecording();
        mCsvWorker.close();
    }

    /**
     * Calls every sensor data update
     *
     * @param data
     */
    private void onDataUpdate(ImuData data) {
        getViewState().showData(data);
        if (mRecordDuration.isRecording()) {
            if (shouldWriteNewFile()) {
                mCsvWorker = new CsvWorker();
                mTimeFromLastIteration = System.currentTimeMillis();
                startWriteFile();
            }
            mRecordDuration.updateDuration(System.currentTimeMillis());
            mCsvWorker.writeLine(data.toCsv());
            getViewState().updateRecordingData(mRecordDuration);
        }
    }

    /**
     * Creates new file for recording
     */
    private void startWriteFile() {
        try {
            mCsvWorker.createOrOpen();
        } catch (IOException e) {
            e.printStackTrace();
            mRecordDuration.stopRecording();
            getViewState().stopRecording();
            getViewState().showMessage(R.string.error_write_permission_denied);
        }
    }

    /**
     * Check if write time limit gained
     *
     * @return
     */
    private boolean shouldWriteNewFile() {
        long timeWriteLimit = 60 * 60 * 1000;//60 min
        return (System.currentTimeMillis() - mTimeFromLastIteration) >= timeWriteLimit;
    }
}
