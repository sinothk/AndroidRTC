package com.sinothk.webrtc.android;

import android.os.Handler;
import android.os.Looper;

import com.dds.webrtclib.PeerConnectionHelper;
import com.dds.webrtclib.bean.MyIceServer;
import com.dds.webrtclib.ws.IConnectEvent;
import com.dds.webrtclib.ws.ISignalingEvents;
import com.dds.webrtclib.ws.IWebSocket;
import com.dds.webrtclib.ws.JavaWebSocket;

import org.webrtc.IceCandidate;

import java.util.ArrayList;
import java.util.List;

public class RtcHelper implements ISignalingEvents {

    private static IWebSocket _webSocket;
    private static PeerConnectionHelper _peerHelper;

    private static String _wss;
    private static MyIceServer[] _iceServers;
    private static IConnectEvent _connectEvent;

    private static RtcHelper rtcHelper;

    public static void init(String serverUrl, MyIceServer[] iceServers, IConnectEvent event) {
        _wss = serverUrl;
        _iceServers = iceServers;
        _connectEvent = event;

        rtcHelper = new RtcHelper();
        rtcHelper.connect();
    }

    // connect
    private void connect() {

        if (_webSocket != null) {
            // 正在通话中
            _webSocket.close();
            _webSocket = null;
            _peerHelper = null;
        }

        _webSocket = new JavaWebSocket(this);
        _webSocket.connect(_wss);
        _peerHelper = new PeerConnectionHelper(_webSocket, _iceServers);
    }

    public static void joinRoom(String _roomId) {
        if (_webSocket != null) {
            _webSocket.joinRoom(_roomId);
        }
    }

    // ================================================================================
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onWebSocketOpen() {
//        handler.post(() -> {
//            if (_connectEvent != null) {
//                _connectEvent.onSuccess();
//            }
//
//        });

        if (_webSocket == null) {

        }
    }

    @Override
    public void onWebSocketOpenFailed(String msg) {
//        handler.post(() -> {
//            if (_webSocket != null && !_webSocket.isOpen()) {
//                _connectEvent.onFailed(msg);
//            } else {
//                if (_peerHelper != null) {
//                    _peerHelper.exitRoom();
//                }
//            }
//        });
        if (_webSocket == null) {

        }
    }

    @Override
    public void onJoinToRoom(ArrayList<String> connections, String myId) {
//        handler.post(() -> {
//            if (_peerHelper != null) {
//                _peerHelper.onJoinToRoom(connections, myId, _videoEnable, _mediaType);
//                if (_mediaType == MediaType.TYPE_VIDEO || _mediaType == MediaType.TYPE_MEETING) {
//                    toggleSpeaker(true);
//                }
//            }
//        });
        if (_webSocket == null) {

        }
    }

    @Override
    public void onRemoteJoinToRoom(String socketId) {
//        handler.post(() -> {
//            if (_peerHelper != null) {
//                _peerHelper.onRemoteJoinToRoom(socketId);
//
//            }
//        });
        if (_webSocket == null) {

        }
    }

    @Override
    public void onRemoteIceCandidate(String socketId, IceCandidate iceCandidate) {
//        handler.post(() -> {
//            if (_peerHelper != null) {
//                _peerHelper.onRemoteIceCandidate(socketId, iceCandidate);
//            }
//        });
        if (_webSocket == null) {

        }
    }

    @Override
    public void onRemoteIceCandidateRemove(String socketId, List<IceCandidate> iceCandidates) {
//        handler.post(() -> {
//            if (_peerHelper != null) {
//                _peerHelper.onRemoteIceCandidateRemove(socketId, iceCandidates);
//            }
//        });
        if (_webSocket == null) {

        }
    }

    @Override
    public void onRemoteOutRoom(String socketId) {
//        handler.post(() -> {
//            if (_peerHelper != null) {
//                _peerHelper.onRemoteOutRoom(socketId);
//            }
//        });
        if (_webSocket == null) {

        }
    }

    @Override
    public void onReceiveOffer(String socketId, String sdp) {
//        handler.post(() -> {
//            if (_peerHelper != null) {
//                _peerHelper.onReceiveOffer(socketId, sdp);
//            }
//        });
        if (_webSocket == null) {

        }
    }

    @Override
    public void onReceiverAnswer(String socketId, String sdp) {
//        handler.post(() -> {
//            if (_peerHelper != null) {
//                _peerHelper.onReceiverAnswer(socketId, sdp);
//            }
//        });
        if (_webSocket == null) {

        }
    }

    @Override
    public void onReceiverMsg(String msg) {
//        handler.post(() -> {
//            if (_peerHelper != null) {
//                _peerHelper.onReceiverMsg(msg);
//            }
//        });
        if (_webSocket == null) {

        }
    }

    @Override
    public void onReceiverOnlineList(String message) {
//        handler.post(() -> {
//            if (_peerHelper != null) {
//                _peerHelper.onReceiverOnlineList(message);
//            }
//        });
        if (_webSocket == null) {

        }
    }

    // ================================================================================
}
