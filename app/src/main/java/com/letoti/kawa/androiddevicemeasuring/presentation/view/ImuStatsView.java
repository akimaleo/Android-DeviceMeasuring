package com.letoti.kawa.androiddevicemeasuring.presentation.view;

import com.arellomobile.mvp.MvpView;
import com.letoti.kawa.androiddevicemeasuring.domain.entity.ImuData;
import com.letoti.kawa.androiddevicemeasuring.presentation.features.table.RecordingData;

public interface ImuStatsView extends BaseView {

    void showData(ImuData data);

    void updateRecordingData(RecordingData data);

    void startRecording();

    void stopRecording();
}
