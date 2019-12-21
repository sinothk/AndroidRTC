package com.zkhy.webrtc.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dds.webrtclib.ui.ChatRoomActivity;
import com.sinothk.webrtc.android.RoomEventListener;
import com.sinothk.webrtc.android.RtcHelper;

import java.util.ArrayList;

public class RtcDemoActivity extends AppCompatActivity {

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
            RtcHelper.joinRoom(roomNum, new RoomEventListener() {
                @Override
                public void onSuccess(ArrayList<String> connections, String myId) {
                    ChatRoomActivity.openActivity(RtcDemoActivity.this);
                }

                @Override
                public void onFails(String myId) {

                }
            });
        });
    }
}
