//package com.zkhy.webrtc.android;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.EditText;
//
///**
// * Created by dds on 2018/11/7.
// * android_shuai@163.com
// */
//public class MainActivityBackup extends AppCompatActivity {
//
//    private EditText et_signal;
//    private EditText et_port;
//    private EditText et_room;
//
//    private EditText edit_test_wss;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.content_main);
//
//        initView();
//        initVar();
//
//    }
//
//    private void initView() {
//        et_signal = findViewById(R.id.et_signal);
//        et_port = findViewById(R.id.et_port);
//        et_room = findViewById(R.id.et_room);
//        edit_test_wss = findViewById(R.id.et_wss);
//    }
//
//    private void initVar() {
//        et_signal.setText("ws://192.168.1.124");
//        et_port.setText("3000");
//        et_room.setText("1");
//
//        edit_test_wss.setText("wss://192.168.1.124:4436");
//    }
//
//    public void JoinRoomSingleVideo(View view) {// 一对一视频
//
//        WebrtcUtil.callSingle(this,
//                et_signal.getText().toString() + ":" + et_port.getText().toString().trim(),
//                et_room.getText().toString().trim(), //et_room.getText().toString().trim() + ":" + et_port.getText().toString().trim()
//                true);
//    }
//
//    public void JoinRoomSingleAudio(View view) { // 一对一语音
//        WebrtcUtil.callSingle(this,
//                et_signal.getText().toString() + ":" + et_port.getText().toString().trim(),
//                et_room.getText().toString().trim(),//et_room.getText().toString().trim() + ":" + et_port.getText().toString().trim(),
//                false);
//    }
//
//    public void JoinRoom(View view) {// 加入房间
//        WebrtcUtil.call(this, et_signal.getText().toString() + ":" + et_port.getText().toString().trim(), et_room.getText().toString().trim());
//
//    }
//
//    //test wss
//    public void wss(View view) {
//        WebrtcUtil.testWs(edit_test_wss.getText().toString());
//    }
//
//    public void call001(View view) {
//        WebrtcUtil.sendMsg(this, et_signal.getText().toString() + ":" + et_port.getText().toString().trim(), et_room.getText().toString().trim() , "内容");
//    }
//
//    public void setId(View view) {
//        WebrtcUtil.sendId(this, et_signal.getText().toString() + ":" + et_port.getText().toString().trim(), "6453");
//    }
//
//}
