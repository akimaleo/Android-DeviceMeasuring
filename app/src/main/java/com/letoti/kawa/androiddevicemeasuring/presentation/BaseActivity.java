package com.letoti.kawa.androiddevicemeasuring.presentation;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.letoti.kawa.androiddevicemeasuring.presentation.view.BaseView;

@SuppressLint("Registered")
public class BaseActivity extends MvpAppCompatActivity implements BaseView {
    @Override
    public void showMessage(int message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
