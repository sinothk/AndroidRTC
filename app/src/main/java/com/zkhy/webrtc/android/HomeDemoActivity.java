package com.zkhy.webrtc.android;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dds.webrtclib.IViewCallback;
import com.dds.webrtclib.WebRTCManager;
import com.dds.webrtclib.bean.ChatType;
import com.dds.webrtclib.bean.Constant;
import com.dds.webrtclib.bean.MediaType;
import com.dds.webrtclib.bean.MeetingContent;
import com.dds.webrtclib.bean.MeetingMsg;
import com.dds.webrtclib.bean.MsgType;
import com.dds.webrtclib.ui.ChatRoomActivity;
import com.dds.webrtclib.ui.ChatSingleActivity;
import com.dds.webrtclib.ui.plugin.VoiLoadingActivity;
import com.dds.webrtclib.ws.IConnectEvent;

import org.webrtc.MediaStream;

/**
 * Created by dds on 2018/11/7.
 * android_shuai@163.com
 */
public class HomeDemoActivity extends AppCompatActivity implements IViewCallback {

    private EditText roomIdEt, targetIdIdv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        roomIdEt = findViewById(R.id.roomId);
        targetIdIdv = findViewById(R.id.targetId_id_v);
        WebRTCManager.getInstance().setCallback(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(this::initWebRtc, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebRTCManager.getInstance().closeConnect();
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

    public void joinRoom(View view) {
        // 默契加入房间
        String wss = Constant.url;
        String roomId = roomIdEt.getText().toString();

        WebRTCManager.getInstance().init(wss, Constant.iceServers, new IConnectEvent() {
            @Override
            public void onSuccess() {
                initUserInfo();

                ChatRoomActivity.openActivity(HomeDemoActivity.this);
            }

            @Override
            public void onFailed(String msg) {
                if (msg == null) {

                }
            }
        });

        WebRTCManager.getInstance().connect(MediaType.TYPE_MEETING, roomId);
    }

    public void joinRoomByTip(View view) {
        // 提醒加入房间
        String sendId = Constant.userId;
        String targetIds = targetIdIdv.getText().toString().trim();
        String roomId = roomIdEt.getText().toString().trim();

        meetingRequest(sendId, targetIds, roomId);
        ChatRoomActivity.openActivity(HomeDemoActivity.this, roomId);
    }

    private void meetingRequest(String sendId, String targetIds, String roomId) {
        MeetingMsg meetingMsg = new MeetingMsg();
        meetingMsg.setEventName("NOTICE");
        meetingMsg.setServerFunc("sayHello");
        meetingMsg.setClientFunc("retHello");
        meetingMsg.setSendId(sendId);
        meetingMsg.setTargetIds(targetIds);
        meetingMsg.setMsgType(MsgType.MEET_REQUEST);
        meetingMsg.setChatType(ChatType.MEETING);

        MeetingContent meetingContent = new MeetingContent();
        meetingContent.setMsg("这是发送内容");
        meetingContent.setRoomId(roomId);

        meetingMsg.setData(meetingContent);

        WebRTCManager.getInstance().sendMsg(meetingMsg);
    }

    public void joinSingleVideo(View view) {

        String sendId = Constant.userId;
        String targetIds = targetIdIdv.getText().toString().trim();
        String roomId = roomIdEt.getText().toString().trim();

        Intent intent = new Intent(HomeDemoActivity.this, VoiLoadingActivity.class);
        intent.putExtra("sendId", sendId);
        intent.putExtra("targetIds", targetIds);
        intent.putExtra("roomId", roomId);
        startActivity(intent);
    }

    // 接收消息部分
    @Override
    public void onReceiverMsg(MeetingMsg meetingMsg) {

        if (meetingMsg.getChatType() == ChatType.MEETING) {
            if (meetingMsg.getMsgType() == MsgType.MEET_REQUEST) {

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeDemoActivity.this);
                builder.setTitle("房间视频请求");
                builder.setItems(new String[]{"接收", "拒接"}, (dialogInterface, i) -> {
                    if (i == 0) {
                        callBack(WebRTCManager.getInstance(), meetingMsg, true);
                        ChatRoomActivity.openActivity(HomeDemoActivity.this, meetingMsg.getData().getRoomId());

                    } else if (i == 1) {
                        callBack(WebRTCManager.getInstance(), meetingMsg, false);
                        Toast.makeText(HomeDemoActivity.this, "我拒接", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setCancelable(true);
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(HomeDemoActivity.this, "未知消息", Toast.LENGTH_SHORT).show();
            }
        } else if (meetingMsg.getChatType() == ChatType.SINGLE) {

            if (meetingMsg.getMsgType() == MsgType.MEET_REQUEST) {

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeDemoActivity.this);
                builder.setTitle("一对一视频请求");
                builder.setItems(new String[]{"接收", "拒接"}, (dialogInterface, i) -> {
                    if (i == 0) {
                        callBack(WebRTCManager.getInstance(), meetingMsg, true);

                        ChatSingleActivity.openActivity(HomeDemoActivity.this, true, meetingMsg.getData().getRoomId());

                    } else if (i == 1) {
                        callBack(WebRTCManager.getInstance(), meetingMsg, false);
                        Toast.makeText(HomeDemoActivity.this, "拒接对方", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setCancelable(true);
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Toast.makeText(HomeDemoActivity.this, "未知消息", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onReceiverOnlineList(MeetingMsg meetingMsg) {
        // 进入退出房间返回房间信息
        if (meetingMsg != null && meetingMsg.getData() != null && meetingMsg.getData().getRoomClients() != null) {
        }
    }

    private void callBack(WebRTCManager manager, MeetingMsg meetingMsg, boolean status) {

        MeetingMsg msgBack = new MeetingMsg();
        msgBack.setEventName("NOTICE");
        msgBack.setServerFunc("sayHello");
        msgBack.setClientFunc("retHello");
        msgBack.setSendId(meetingMsg.getTargetIds()); // 发送/接收对象替换
        msgBack.setTargetIds(meetingMsg.getSendId());
        msgBack.setMsgType(MsgType.MEET_RESPONSE);

        MeetingContent meetingContent = new MeetingContent();
        meetingContent.setReceived(status);

        msgBack.setData(meetingContent);

        manager.sendMsg(msgBack);
    }

    public void doReconnect(View view) {
        initWebRtc();
    }

    private void initWebRtc() {

        String roomId = roomIdEt.getText().toString().trim();

        WebRTCManager.getInstance().init(Constant.url, Constant.iceServers, new IConnectEvent() {
            @Override
            public void onSuccess() {
                initUserInfo();
            }

            @Override
            public void onFailed(String msg) {
                if (msg != null) {
                    Toast.makeText(HomeDemoActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        WebRTCManager.getInstance().connect();
        WebRTCManager.getInstance().setCallback(this);
    }

    private void initUserInfo() {

        MeetingMsg meetingMsg = new MeetingMsg();
        meetingMsg.setEventName("__joinUser");

        MeetingContent meetingContent = new MeetingContent();
        meetingContent.setId(Constant.userId);
        meetingContent.setName("梁玉涛");
        meetingContent.setPhoto("https://pics3.baidu.com/feed/5243fbf2b2119313d19c34748d1912d290238d3a.jpeg?token=a57fa0ed50a8eacbd825572052c6fe4b&s=AA9310C5145A87D01210A59603007002");
        meetingMsg.setData(meetingContent);

        WebRTCManager.getInstance().sendMsg(meetingMsg);

    }
}
