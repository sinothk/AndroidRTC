package com.zkhy.webrtc.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sinothk.webrtc.android.RtcHelper;

public class RtcDemoActivity extends AppCompatActivity {

    private Button createRoomBtn;

    private EditText roomNumEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_demo);

        createRoomBtn = findViewById(R.id.createRoomBtn);
        roomNumEt = findViewById(R.id.roomNumEt);
        createRoomBtn.setOnClickListener(view -> {
            String roomNum = roomNumEt.getText().toString();
            RtcHelper.joinRoom(roomNum);
        });
    }
}
