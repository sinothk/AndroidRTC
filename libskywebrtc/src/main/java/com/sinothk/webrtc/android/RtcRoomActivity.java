package com.sinothk.webrtc.android;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.dds.webrtclib.IViewCallback;
import com.dds.webrtclib.PeerConnectionHelper;
import com.dds.webrtclib.ProxyVideoSink;
import com.dds.webrtclib.R;
import com.dds.webrtclib.bean.Constant;
import com.dds.webrtclib.bean.DataCache;
import com.dds.webrtclib.bean.MeetingMsg;
import com.dds.webrtclib.bean.MeetingUserEntity;
import com.dds.webrtclib.inters.OnMeetEvent;
import com.dds.webrtclib.ui.UserListViewActivity;
import com.dds.webrtclib.utils.PermissionUtil;

import org.webrtc.EglBase;
import org.webrtc.MediaStream;
import org.webrtc.RendererCommon;
import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoTrack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 群聊界面
 * 支持 N 路同時通信
 */
public class RtcRoomActivity extends UserListViewActivity implements IViewCallback, OnMeetEvent {

    private FrameLayout wr_video_view_big;
    private RtcHelper manager;

    private Map<String, SurfaceViewRenderer> _videoViews = new HashMap<>();
    private Map<String, ProxyVideoSink> _sinks = new HashMap<>();
    private List<MeetingUserEntity> _infos = new ArrayList<>();
    private VideoTrack _localVideoTrack;

    private String roomId;
    private EglBase rootEglBase;

    private MeetingUserEntity meetingCurrUser;

    public static void openActivity(Activity activity, String roomId) {
        Intent intent = new Intent(activity, RtcRoomActivity.class);
        intent.putExtra("roomId", roomId);
        activity.startActivityForResult(intent, 200);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        roomId = getIntent().getStringExtra("roomId");

        manager = RtcHelper.getInstance();

        wr_video_view_big = findViewById(R.id.wr_video_view_big);
        rootEglBase = EglBase.create();

        startCall();

        RtcRoomFragment chatRoomFragment = new RtcRoomFragment();
        replaceFragment(chatRoomFragment);
    }

    private void startCall() {
        manager.setCallback(this);
        manager.setMeetEvent(this);

        if (!PermissionUtil.isNeedRequestPermission(RtcRoomActivity.this)) {
            manager.joinRoom(getApplicationContext(), roomId, rootEglBase);
        }
    }

    @Override
    public void onSetLocalStream(MediaStream stream, String userId) {
        List<VideoTrack> videoTracks = stream.videoTracks;
        if (videoTracks.size() > 0) {
            _localVideoTrack = videoTracks.get(0);
        }
        addView(userId, stream);
    }

    @Override
    public void onAddRemoteStream(MediaStream stream, String userId) {
        addView(userId, stream);
    }

    @Override
    public void onCloseWithId(String userId) {
        runOnUiThread(() -> removeView(userId));
    }

    @Override
    public void onReceiverMsg(MeetingMsg meetingMsg) {
        if (meetingMsg == null) {
            return;
        }
    }

    private void addView(String id, MediaStream stream) {
        runOnUiThread(() -> {
            SurfaceViewRenderer renderer = new SurfaceViewRenderer(RtcRoomActivity.this);
            renderer.init(rootEglBase.getEglBaseContext(), null);
            renderer.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FIT);
            renderer.setMirror(true);

            // set render
            ProxyVideoSink sink = new ProxyVideoSink();
            sink.setTarget(renderer);
            if (stream.videoTracks.size() > 0) {
                stream.videoTracks.get(0).addSink(sink);
            }
            _videoViews.put(id, renderer);
            _sinks.put(id, sink);
            _infos.add(new MeetingUserEntity(id));

            // 添加后，重新计算绘制界面
            if (meetingCurrUser == null) {
                meetingCurrUser = _infos.get(0);

                SurfaceViewRenderer renderer1 = _videoViews.get(meetingCurrUser.getUserId());
                if (renderer1 != null) {
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                    renderer1.setLayoutParams(layoutParams);

                    wr_video_view_big.removeAllViews();
                    wr_video_view_big.addView(renderer1);
                }
            }
        });
    }

    @Override
    public void onEnterOrExitRoom(MeetingMsg meetingMsg) {
        // 进入退出房间返回房间信息
        if (meetingMsg != null && meetingMsg.getData() != null && meetingMsg.getData().getRoomClients() != null) {

            ArrayList<MeetingUserEntity> userList = meetingMsg.getData().getRoomClients();
            if (meetingCurrUser == null) {
                for (int i = 0; userList.size() > i; i++) {
                    if (userList.get(i).getUserId().equals(Constant.userId)) {
                        userList.get(i).setCurrLiving(true);
                    } else {
                        userList.get(i).setCurrLiving(false);
                    }
                }
            } else {
                for (int i = 0; userList.size() > i; i++) {
                    if (userList.get(i).getUserId().equals(meetingCurrUser.getUserId())) {
                        userList.get(i).setCurrLiving(true);
                    } else {
                        userList.get(i).setCurrLiving(false);
                    }
                }
            }

            runOnUiThread(() -> setGridView(userList)
            );
        }
    }

    @Override
    public void setItemClick(MeetingUserEntity meetingUser) {
        meetingCurrUser = meetingUser;
        if (meetingCurrUser == null) {
            return;
        }

        SurfaceViewRenderer currRenderer = _videoViews.get(meetingCurrUser.getUserId());
        if (currRenderer != null) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            currRenderer.setLayoutParams(layoutParams);

            wr_video_view_big.removeAllViews();
            wr_video_view_big.addView(currRenderer);
        }
    }

    private void removeView(String userId) {
        //
        ProxyVideoSink sink = _sinks.get(userId);
        SurfaceViewRenderer renderer = _videoViews.get(userId);
        if (sink != null) {
            sink.setTarget(null);
        }
        if (renderer != null) {
            renderer.release();
        }
        _sinks.remove(userId);
        _videoViews.remove(userId);
        _infos.remove(new MeetingUserEntity(userId));

        wr_video_view_big.removeView(renderer);

        // 重新展示
        if (!TextUtils.isEmpty(meetingCurrUser.getUserId()) && userId.equals(meetingCurrUser.getUserId())) {
            meetingCurrUser = null;

            if (meetingUserAdapter.getDataList().size() > 0) {
                meetingCurrUser = meetingUserAdapter.getDataList().get(0);
                SurfaceViewRenderer renderer1 = _videoViews.get(meetingCurrUser.getUserId());
                if (renderer1 != null) {
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                    renderer1.setLayoutParams(layoutParams);

                    wr_video_view_big.removeAllViews();
                    wr_video_view_big.addView(renderer1);
                }

                meetingUserAdapter.setLivingUser(meetingCurrUser.getUserId());
            }
        }
    }

    @Override  // 屏蔽返回键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.wr_container, fragment)
                .commit();
    }

    // 切换摄像头
    public void switchCamera() {
        manager.switchCamera();
    }

    // 挂断
    public void hangUp() {
        DataCache.clearOnlineUserInfo();
        exit();
        finish();
    }

    // 静音
    public void toggleMic(boolean enable) {
        manager.toggleMute(enable);
    }

    // 免提
    public void toggleSpeaker(boolean enable) {
        manager.toggleSpeaker(enable);
    }

    // 打开关闭摄像头
    public void toggleCamera(boolean enableCamera) {
        if (_localVideoTrack != null) {
            _localVideoTrack.setEnabled(enableCamera);
        }
    }

    private void exit() {
        manager.exitRoom();

        for (SurfaceViewRenderer renderer : _videoViews.values()) {
            renderer.release();
        }

        for (ProxyVideoSink sink : _sinks.values()) {
            sink.setTarget(null);
        }

        _videoViews.clear();
        _sinks.clear();
        _infos.clear();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int i = 0; i < permissions.length; i++) {
            Log.i(PeerConnectionHelper.TAG, "[Permission] " + permissions[i] + " is " + (grantResults[i] == PackageManager.PERMISSION_GRANTED ? "granted" : "denied"));
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                finish();
                break;
            }
        }
        manager.joinRoom(getApplicationContext(), roomId, rootEglBase);
    }
}
