package com.letoti.kawa.androiddevicemeasuring.presentation.view;

import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpView;

public interface BaseView extends MvpView {
    void showMessage(@StringRes int message);
}
