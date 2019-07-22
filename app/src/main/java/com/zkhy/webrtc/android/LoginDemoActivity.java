package com.zkhy.webrtc.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dds.webrtclib.WebRTCManager;
import com.dds.webrtclib.bean.Constant;
import com.dds.webrtclib.bean.MeetingContent;
import com.dds.webrtclib.bean.MeetingMsg;
import com.dds.webrtclib.bean.MyIceServer;
import com.dds.webrtclib.ws.IConnectEvent;

/**
 * Created by dds on 2018/11/7.
 * android_shuai@163.com
 */
public class LoginDemoActivity extends AppCompatActivity {

    private EditText et_signal;
    private EditText et_port;
    private EditText etId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_signal = findViewById(R.id.et_signal);
        et_port = findViewById(R.id.et_port);
        etId = findViewById(R.id.et_id);

        et_signal.setText("ws://192.168.1.124");
        et_port.setText("3000");
        etId.setText("6666");
    }

    public void startMeeting(View view) {// 进入原生
        startActivity(new Intent(this, MainActivity.class));
    }

    // turn and stun

    public void setId(View view) {

        Constant.url = et_signal.getText().toString() + ":" + et_port.getText().toString().trim();
        //"wss://112.74.58.200/wss";

        Constant.userId = etId.getText().toString();

//        Constant.iceServers = new MyIceServer[]{
//                // new MyIceServer("stun:stun.l.google.com:19302"),
//                new MyIceServer("stun:47.254.34.146:3478"),
//                new MyIceServer("turn:47.254.34.146?transport=tcp", "dds", "123456"),
//                new MyIceServer("turn:47.254.34.146:3478", "dds", "123456"),
//        };

        Constant.iceServers = new MyIceServer[]{
                // new MyIceServer("stun:stun.l.google.com:19302"),
                new MyIceServer("turn:47.106.221.149:3478", "wz", "123456"),
        };

        WebRTCManager.getInstance().init(Constant.url, Constant.iceServers, new IConnectEvent() {
            @Override
            public void onSuccess() {
                initUserInfo();
                startActivity(new Intent(LoginDemoActivity.this, HomeDemoActivity.class));
            }

            @Override
            public void onFailed(String msg) {
                if (msg != null) {
                    Toast.makeText(LoginDemoActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        WebRTCManager.getInstance().connect();
    }

    private void initUserInfo() {

        MeetingMsg meetingMsg = new MeetingMsg();
        meetingMsg.setEventName("__joinUser");

        MeetingContent meetingContent = new MeetingContent();
        meetingContent.setId(Constant.userId);
        meetingMsg.setData(meetingContent);

        WebRTCManager.getInstance().sendMsg(meetingMsg);

    }
}
