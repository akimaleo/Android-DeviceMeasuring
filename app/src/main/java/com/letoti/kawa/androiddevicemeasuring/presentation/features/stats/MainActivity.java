package com.letoti.kawa.androiddevicemeasuring.presentation.features.stats;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.letoti.kawa.androiddevicemeasuring.R;
import com.letoti.kawa.androiddevicemeasuring.data.imu.ImuUtilImpl;
import com.letoti.kawa.androiddevicemeasuring.domain.entity.ImuData;
import com.letoti.kawa.androiddevicemeasuring.presentation.BaseActivity;
import com.letoti.kawa.androiddevicemeasuring.presentation.features.table.ImuTableView;
import com.letoti.kawa.androiddevicemeasuring.presentation.features.table.RecordingData;
import com.letoti.kawa.androiddevicemeasuring.presentation.presenter.ImuStatsPresenter;
import com.letoti.kawa.androiddevicemeasuring.presentation.view.ImuStatsView;

public class MainActivity extends BaseActivity implements ImuStatsView {

    static final int PERMISSION_REQUEST_CODE = 13;

    @InjectPresenter(type = PresenterType.GLOBAL)
    ImuStatsPresenter mPresenter;
    ImuTableView mImuDataTable;
    ImageView mRecordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mImuDataTable = findViewById(R.id.imu_table);
        mImuDataTable.setData(new ImuData());

        mRecordButton = findViewById(R.id.record);
        mRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasWritePermissions()) {
                    mPresenter.toggleRecording();
                } else {
                    requestPerms();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.startDataFetching();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.stopDataFetching();
    }


    @ProvidePresenter(type = PresenterType.GLOBAL)
    ImuStatsPresenter provideImuStatsPresenter() {
        ImuStatsPresenter imuStatsPresenter = new ImuStatsPresenter();
        imuStatsPresenter.setmImuUtil(new ImuUtilImpl(this));
        return imuStatsPresenter;
    }

    @Override
    public void showData(ImuData data) {
        mImuDataTable.setData(data);
    }

    @Override
    public void updateRecordingData(RecordingData data) {
        mImuDataTable.setRecordingData(data);
    }

    @Override
    public void startRecording() {
        mRecordButton.setImageResource(R.drawable.ic_stop_black_24dp);
    }

    @Override
    public void stopRecording() {
        mRecordButton.setImageResource(R.drawable.ic_fiber_manual_record_red_24dp);
    }


    /**
     * We can use RxPermissions for requesting permission
     * and keep code more clear
     *
     * @return
     */
    private boolean hasWritePermissions() {
        int res = 0;
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        for (String perms : permissions) {
            res = checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    private void requestPerms() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPresenter.toggleRecording();
                } else {
                    showMessage(R.string.error_write_permission_denied);
                }
                break;
        }
    }
}
