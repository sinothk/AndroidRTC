package com.zkhy.webrtc.android;

import android.app.Application;

import com.sinothk.webrtc.android.ARtcUtil;

public class ARtcApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ARtcUtil.init();
    }
}
