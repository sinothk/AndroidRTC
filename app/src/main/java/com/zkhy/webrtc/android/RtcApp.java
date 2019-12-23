package com.zkhy.webrtc.android;

import android.app.Application;

import com.dds.webrtclib.bean.MeetingContent;
import com.dds.webrtclib.bean.MeetingMsg;
import com.dds.webrtclib.bean.MyIceServer;
import com.dds.webrtclib.ws.IConnectEvent;
import com.sinothk.webrtc.android.RtcHelper;

public class RtcApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initRtc();
    }

    public static void initRtc() {

//        String serverUrl = "ws://192.168.1.124:3000";

        String serverUrl = "wss://112.74.58.200/wss";
//        String serverUrl = "wss://192.168.1.124/wss";

        RtcHelper.init(serverUrl, iceServers, new IConnectEvent() {
            @Override
            public void onSuccess() {
                //
                MeetingMsg meetingMsg = new MeetingMsg();

                MeetingContent meetingContent = new MeetingContent();
                meetingContent.setId("15285536455");
                meetingContent.setName("张三");
                meetingContent.setPhoto("https://pics7.baidu.com/feed/a8ec8a13632762d0c58a86885537e6ff503dc6b6.jpeg?token=2e6b03ff38e968aa9f33067e4c0418bf&s=68B000D7C8003CD20499E9A30300F003");

                meetingMsg.setData(meetingContent);
                meetingMsg.setEventName("__joinUser");

                RtcHelper.getInstance().sendMsg(meetingMsg);
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
            // new MyIceServer("stun:stun.l.google.com:19302"), https://112.74.58.200
            new MyIceServer("stun:112.74.58.200:3478"),
            new MyIceServer("turn:112.74.58.200?transport=tcp", "wz", "123456"),
            new MyIceServer("turn:112.74.58.200:3478", "wz", "123456"),
    };

//    private static MyIceServer[] iceServers = {
//            // new MyIceServer("stun:stun.l.google.com:19302"), https://112.74.58.200
//            new MyIceServer("stun:112.74.58.200:3478"),
//            new MyIceServer("turn:112.74.58.200?transport=tcp", "dds", "123456"),
//            new MyIceServer("turn:112.74.58.200:3478", "dds", "123456"),
//    };
}
