package com.zkhy.webrtc.android;

import android.app.Application;

import com.dds.webrtclib.bean.MyIceServer;
import com.dds.webrtclib.ws.IConnectEvent;
import com.sinothk.webrtc.android.RtcHelper;

public class RtcApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        String serverUrl = "ws://192.168.1.124:3000";

        RtcHelper.init(serverUrl, iceServers, new IConnectEvent() {
            @Override
            public void onSuccess() {
                if (serverUrl == null) {

                }
            }

            @Override
            public void onFailed(String msg) {
                if (msg == null) {

                }
            }
        });
    }

    // turn and stun
    private static MyIceServer[] iceServers = {
            // new MyIceServer("stun:stun.l.google.com:19302"),
            new MyIceServer("stun:47.254.34.146:3478"),
            new MyIceServer("turn:47.254.34.146?transport=tcp", "dds", "123456"),
            new MyIceServer("turn:47.254.34.146:3478", "dds", "123456"),
    };
}
