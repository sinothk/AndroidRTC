package com.dds.webrtclib;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.dds.webrtclib.bean.MediaType;
import com.dds.webrtclib.bean.MeetingMsg;
import com.dds.webrtclib.bean.MyIceServer;
import com.dds.webrtclib.ws.IConnectEvent;
import com.dds.webrtclib.ws.ISignalingEvents;
import com.dds.webrtclib.ws.IWebSocket;
import com.dds.webrtclib.ws.JavaWebSocket;

import org.webrtc.EglBase;
import org.webrtc.IceCandidate;

import java.util.ArrayList;
import java.util.List;

/**
 * 控制信令和各种操作
 * Created by dds on 2019/4/5.
 * android_shuai@163.com
 */
public class WebRTCManager implements ISignalingEvents {

    private final static String TAG = "sing_WebRTCManager";
    private String _wss;
    private MyIceServer[] _iceServers;

    private IWebSocket _webSocket;
    private PeerConnectionHelper _peerHelper;

    private String _roomId;
    private int _mediaType;
    private boolean _videoEnable;


    private IConnectEvent _connectEvent;
    private Handler handler = new Handler(Looper.getMainLooper());

    public static WebRTCManager getInstance() {
        return Holder.wrManager;
    }

    private static class Holder {
        private static WebRTCManager wrManager = new WebRTCManager();
    }

    // init address
    public void init(String wss, MyIceServer[] iceServers, IConnectEvent event) {
        this._wss = wss;
        this._iceServers = iceServers;
        _connectEvent = event;
    }

    // connect
    public void connect(int mediaType, String roomId) {

        if (_webSocket != null) {
            // 正在通话中
            _webSocket.close();
            _webSocket = null;
            _peerHelper = null;
        }

        _mediaType = mediaType;
        _videoEnable = mediaType != MediaType.TYPE_AUDIO;

        _roomId = roomId;
        _webSocket = new JavaWebSocket(this);
        _webSocket.connect(_wss);
        _peerHelper = new PeerConnectionHelper(_webSocket, _iceServers);
    }

    public void sendRoomId(String roomId) {
        _roomId = roomId;
    }

    public void sendMediaType(int mediaType) {
        _mediaType = mediaType;
    }

    public void sendVideoEnable(boolean videoEnable) {
        _videoEnable = videoEnable;
    }

    public void setCallback(IViewCallback callback) {
        if (_peerHelper != null) {
            _peerHelper.setViewCallback(callback);
        }
    }

    //===================================控制功能==============================================
    public void joinRoom(Context context, EglBase eglBase) {
        if (_peerHelper != null) {
            _peerHelper.initContext(context, eglBase);
        }
        if (_webSocket != null) {
            _webSocket.joinRoom(_roomId);
        }
    }

//    public void joinRoom(Context context, EglBase eglBase, String rootId) {
//        if (_peerHelper != null) {
//            _peerHelper.initContext(context, eglBase);
//        }
//        if (_webSocket != null) {
//            _webSocket.joinRoom(rootId);
//        }
//
//    }

    public void switchCamera() {
        if (_peerHelper != null) {
            _peerHelper.switchCamera();
        }
    }

    public void toggleMute(boolean enable) {
        if (_peerHelper != null) {
            _peerHelper.toggleMute(enable);
        }
    }

    public void toggleSpeaker(boolean enable) {
        if (_peerHelper != null) {
            _peerHelper.toggleSpeaker(enable);
        }
    }

    public void exitRoom() {
        if (_peerHelper != null) {
            _webSocket = null;
            _peerHelper.exitRoom();
        }
    }

    // ==================================信令回调===============================================
    @Override
    public void onWebSocketOpen() {
        handler.post(() -> {
            if (_connectEvent != null) {
                _connectEvent.onSuccess();
            }

        });

    }

    @Override
    public void onWebSocketOpenFailed(String msg) {
        handler.post(() -> {
            if (_webSocket != null && !_webSocket.isOpen()) {
                _connectEvent.onFailed(msg);
            } else {
                if (_peerHelper != null) {
                    _peerHelper.exitRoom();
                }
            }
        });

    }

    @Override
    public void onJoinToRoom(ArrayList<String> connections, String myId) {
        handler.post(() -> {
            if (_peerHelper != null) {
                _peerHelper.onJoinToRoom(connections, myId, _videoEnable, _mediaType);
                if (_mediaType == MediaType.TYPE_VIDEO || _mediaType == MediaType.TYPE_MEETING) {
                    toggleSpeaker(true);
                }
            }
        });

    }

    @Override
    public void onRemoteJoinToRoom(String socketId) {
        handler.post(() -> {
            if (_peerHelper != null) {
                _peerHelper.onRemoteJoinToRoom(socketId);

            }
        });
    }

    @Override
    public void onRemoteIceCandidate(String socketId, IceCandidate iceCandidate) {
        handler.post(() -> {
            if (_peerHelper != null) {
                _peerHelper.onRemoteIceCandidate(socketId, iceCandidate);
            }
        });
    }

    @Override
    public void onRemoteIceCandidateRemove(String socketId, List<IceCandidate> iceCandidates) {
        handler.post(() -> {
            if (_peerHelper != null) {
                _peerHelper.onRemoteIceCandidateRemove(socketId, iceCandidates);
            }
        });

    }

    @Override
    public void onRemoteOutRoom(String socketId) {
        handler.post(() -> {
            if (_peerHelper != null) {
                _peerHelper.onRemoteOutRoom(socketId);
            }
        });

    }

    @Override
    public void onReceiveOffer(String socketId, String sdp) {
        handler.post(() -> {
            if (_peerHelper != null) {
                _peerHelper.onReceiveOffer(socketId, sdp);
            }
        });

    }

    @Override
    public void onReceiverAnswer(String socketId, String sdp) {
        handler.post(() -> {
            if (_peerHelper != null) {
                _peerHelper.onReceiverAnswer(socketId, sdp);
            }
        });
    }

    @Override
    public void onReceiverMsg(String msg) {
        handler.post(() -> {
            if (_peerHelper != null) {
                _peerHelper.onReceiverMsg(msg);
            }
        });
    }

    // ============================

    public void connect() {
        try {
            if (_webSocket != null) {
//                _webSocket.close();
//                _webSocket = null;
//                _peerHelper = null;
                return;
            }

            _webSocket = new JavaWebSocket(this);
            _webSocket.connect(_wss);
            _peerHelper = new PeerConnectionHelper(_webSocket, _iceServers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否已经连接
     *
     * @return
     */
    public boolean isConnected() {
        return _webSocket != null && _webSocket.isOpen();
    }

    public void closeConnect() {
        if (_webSocket != null) {
            // 正在通话中
            _webSocket.close();
            _webSocket = null;
            _peerHelper = null;
        }
    }

    public void sendMsg(MeetingMsg meetingMsg) {
        try {
            _webSocket.sendMsg(meetingMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendId(String sendId) {
        try {
            _webSocket.sendId(sendId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
