package com.zkhy.webrtc.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.dds.webrtclib.IViewCallback;
import com.dds.webrtclib.bean.MeetingMsg;
import com.sinothk.webrtc.android.RtcHelper;
import com.sinothk.webrtc.android.RtcRoomActivity;

import org.webrtc.MediaStream;

public class RtcDemoActivity extends AppCompatActivity implements IViewCallback {

    private Button createRoomBtn, exitRoomBtn;

    private EditText roomNumEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_demo);

        setRoomView();
    }

    private void setRoomView() {
        createRoomBtn = findViewById(R.id.createRoomBtn);
        exitRoomBtn = findViewById(R.id.exitRoomBtn);
        roomNumEt = findViewById(R.id.roomNumEt);

        createRoomBtn.setOnClickListener(view -> {
            String roomNum = roomNumEt.getText().toString();
            RtcRoomActivity.openActivity(RtcDemoActivity.this, roomNum);
        });

        RtcHelper.getInstance().setCallback(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            RtcApp.initRtc();
        }
    }

    @Override
    public void onSetLocalStream(MediaStream stream, String socketId) {

    }

    @Override
    public void onAddRemoteStream(MediaStream stream, String socketId) {

    }

    @Override
    public void onCloseWithId(String socketId) {

    }

    @Override
    public void onReceiverMsg(MeetingMsg msg) {
        if (msg == null) {

        }
    }
}
