package com.dds.webrtclib.ui.plugin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dds.webrtclib.IViewCallback;
import com.dds.webrtclib.R;
import com.dds.webrtclib.WebRTCManager;
import com.dds.webrtclib.bean.ChatType;
import com.dds.webrtclib.bean.MeetingContent;
import com.dds.webrtclib.bean.MeetingMsg;
import com.dds.webrtclib.bean.MsgType;
import com.dds.webrtclib.ui.ChatSingleActivity;

import org.webrtc.EglBase;
import org.webrtc.MediaStream;

/**
 * 呼叫界面
 */
public class VoiLoadingActivity extends AppCompatActivity {

    private String TAG = VoiLoadingActivity.class.getSimpleName();

    private String sendId = "";
    private String targetIds = "";
    private String roomId;

    private WebRTCManager manager;
    private int mScreenWidth;
    private EglBase rootEglBase;

    public void cancelView(View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wr_activity_chat_voi_loading);

        sendId = getIntent().getStringExtra("sendId");
        targetIds = getIntent().getStringExtra("targetIds");
        roomId = getIntent().getStringExtra("roomId");

        initVar();
        startCall();

        meetingRequest(sendId, targetIds, roomId);
    }

    private void initVar() {
        // 设置宽高比例
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        if (manager != null) {
            mScreenWidth = manager.getDefaultDisplay().getWidth();
        }
        rootEglBase = EglBase.create();
    }

    private void startCall() {
        manager = WebRTCManager.getInstance();
        manager.setCallback(new IViewCallback() {

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
            public void onReceiverMsg(MeetingMsg<MeetingContent> meetingMsg) {
                Log.e("onReceiverMsg", "VoiLoadingActivity.onReceiverMsg(String msg) => " + meetingMsg);

                if (meetingMsg.getMsgType() == MsgType.MEET_RESPONSE) {
                    if (meetingMsg.getData().isReceived()) {
                        ChatSingleActivity.openActivity(VoiLoadingActivity.this, true, roomId);
                        Log.e(TAG, "对方已接收");
                        finish();
                    } else {
                        Toast.makeText(VoiLoadingActivity.this, "对方已拒接", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "对方已拒接");
                        finish();
                    }
                } else {
                    Log.e(TAG, "未知消息类型：" + meetingMsg.getMsgType());
                    Toast.makeText(VoiLoadingActivity.this, "未知消息", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void meetingRequest(String sendId, String targetIds, String roomId) {
        MeetingMsg meetingMsg = new MeetingMsg();
        meetingMsg.setEventName("NOTICE");
        meetingMsg.setServerFunc("sayHello");
        meetingMsg.setClientFunc("retHello");
        meetingMsg.setSendId(sendId);
        meetingMsg.setTargetIds(targetIds);
        meetingMsg.setMsgType(MsgType.MEET_REQUEST);
        meetingMsg.setChatType(ChatType.SINGLE);

        MeetingContent meetingContent = new MeetingContent();
        meetingContent.setMsg("这是发送内容");
        meetingContent.setRoomId(roomId);

        meetingMsg.setData(meetingContent);

        WebRTCManager.getInstance().sendMsg(meetingMsg);
    }
}
