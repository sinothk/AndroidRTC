package com.zkhy.webrtc.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dds.webrtclib.IViewCallback;
import com.dds.webrtclib.bean.MeetingMsg;
import com.dds.webrtclib.bean.MsgType;
import com.sinothk.webrtc.android.RtcHelper;
import com.sinothk.webrtc.android.RtcRoomActivity;

import org.webrtc.MediaStream;

public class RtcDemoActivity extends AppCompatActivity implements IViewCallback {

    private Button createRoomBtn;

    private EditText roomNumEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_demo);

        setRoomView();
    }

    private void setRoomView() {
        createRoomBtn = findViewById(R.id.createRoomBtn);
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
        if (msg.getMsgType() == MsgType.MEET_REQUEST) {

            new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher).setTitle("视频邀请")
                    .setMessage("邀请你加入会议").setPositiveButton("接受", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    RtcRoomActivity.openActivity(RtcDemoActivity.this, msg.getData().getRoomId());
                }
            }).setNegativeButton("拒接", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        }
    }

    private void showTip(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
