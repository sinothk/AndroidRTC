package com.dds.webrtclib.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.dds.webrtclib.IViewCallback;
import com.dds.webrtclib.PeerConnectionHelper;
import com.dds.webrtclib.ProxyVideoSink;
import com.dds.webrtclib.R;
import com.dds.webrtclib.WebRTCManager;
import com.dds.webrtclib.bean.MediaType;
import com.dds.webrtclib.bean.MeetingMsg;
import com.dds.webrtclib.bean.MemberBean;
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
public class ChatRoomActivity extends UserListViewActivity implements IViewCallback {

    private FrameLayout wr_video_view_big;

    private WebRTCManager manager;
    private Map<String, SurfaceViewRenderer> _videoViews = new HashMap<>();
    private Map<String, ProxyVideoSink> _sinks = new HashMap<>();
    private List<MemberBean> _infos = new ArrayList<>();
    private VideoTrack _localVideoTrack;

    //    private String roomId;
    private EglBase rootEglBase;

    private static int startType = 0;

    public static void openActivity(Activity activity) {
        startType = 0;

        Intent intent = new Intent(activity, ChatRoomActivity.class);
        activity.startActivity(intent);
    }

    public static void openActivity(Activity activity, String roomId) {
        startType = 1;

        Intent intent = new Intent(activity, ChatRoomActivity.class);
        intent.putExtra("roomId", roomId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = WebRTCManager.getInstance();
        if (startType == 1) {// 邀请加入时
            String roomId = getIntent().getStringExtra("roomId");
            manager.sendRoomId(roomId);
            manager.sendMediaType(MediaType.TYPE_MEETING);
            manager.sendVideoEnable(true);
        }

        wr_video_view_big = findViewById(R.id.wr_video_view_big);
        rootEglBase = EglBase.create();

        startCall();

        ChatRoomFragment chatRoomFragment = new ChatRoomFragment();
        replaceFragment(chatRoomFragment);
    }

    private void startCall() {
        manager.setCallback(this);

        if (!PermissionUtil.isNeedRequestPermission(ChatRoomActivity.this)) {
            manager.joinRoom(getApplicationContext(), rootEglBase);
        }
    }

    @Override
    public void onSetLocalStream(MediaStream stream, String userId) {
        List<VideoTrack> videoTracks = stream.videoTracks;
        if (videoTracks.size() > 0) {
            _localVideoTrack = videoTracks.get(0);
        }
        runOnUiThread(() -> addView(userId, stream));
    }

    @Override
    public void onAddRemoteStream(MediaStream stream, String userId) {
        runOnUiThread(() -> addView(userId, stream));
    }

    @Override
    public void onCloseWithId(String userId) {
        runOnUiThread(() -> removeView(userId));
    }

    @Override
    public void onReceiverMsg(MeetingMsg meetingMsg) {
        if (meetingMsg == null) {
        }
    }

    private void addView(String id, MediaStream stream) {
        SurfaceViewRenderer renderer = new SurfaceViewRenderer(ChatRoomActivity.this);
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
        _infos.add(new MemberBean(id, "用户" + id));

        // 添加后，重新计算绘制界面
        int size = _infos.size();
        for (int i = 0; i < size; i++) {

            if (i == 0) {
                MemberBean memberBean = _infos.get(i);
                SurfaceViewRenderer renderer1 = _videoViews.get(memberBean.getId());
                if (renderer1 != null) {
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                    renderer1.setLayoutParams(layoutParams);

                    wr_video_view_big.removeAllViews();
                    wr_video_view_big.addView(renderer);
                }
            }
        }

        setUserRendererData(_infos);
    }

    @Override
    public void setItemClick(int position) {
        MemberBean memberBean = _infos.get(position);
        SurfaceViewRenderer currRenderer = _videoViews.get(memberBean.getId());

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        currRenderer.setLayoutParams(layoutParams);

        wr_video_view_big.removeAllViews();
        wr_video_view_big.addView(currRenderer);
    }

    private void removeView(String userId) {
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
        _infos.remove(new MemberBean(userId));

        wr_video_view_big.removeView(renderer);

        int size = _infos.size();
        for (int i = 0; i < size; i++) {

            if (i == 0) {
                MemberBean memberBean = _infos.get(i);
                SurfaceViewRenderer renderer1 = _videoViews.get(memberBean.getId());
                if (renderer1 != null) {
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    renderer1.setLayoutParams(layoutParams);

                    wr_video_view_big.removeAllViews();
                    wr_video_view_big.addView(renderer);
                }
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
        manager.joinRoom(getApplicationContext(), rootEglBase);
    }
}
