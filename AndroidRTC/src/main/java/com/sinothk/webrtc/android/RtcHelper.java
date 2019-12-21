package com.sinothk.webrtc.android;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.dds.webrtclib.IViewCallback;
import com.dds.webrtclib.PeerConnectionHelper;
import com.dds.webrtclib.bean.MediaType;
import com.dds.webrtclib.bean.MeetingMsg;
import com.dds.webrtclib.bean.MyIceServer;
import com.dds.webrtclib.inters.OnMeetEvent;
import com.dds.webrtclib.ws.IConnectEvent;
import com.dds.webrtclib.ws.ISignalingEvents;
import com.dds.webrtclib.ws.IWebSocket;
import com.dds.webrtclib.ws.JavaWebSocket;

import org.webrtc.EglBase;
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

    private static int _mediaType;
    private static boolean _videoEnable;

    private static class Holder {
        private static RtcHelper wrManager = new RtcHelper();
    }

    public static RtcHelper getInstance() {
        return Holder.wrManager;
    }

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

    public void joinRoom(Context context, String _roomId, EglBase eglBase) {
        _mediaType = MediaType.TYPE_MEETING;
        _videoEnable = true;


        if (_peerHelper != null) {
            _peerHelper.initContext(context, eglBase);
        }

        if (_webSocket != null) {
            _webSocket.joinRoom(_roomId);
        }
    }

    public void exitRoom() {
        if (_peerHelper != null) {
            _webSocket = null;
            _peerHelper.exitRoom();
        }
    }

    public void sendMsg(MeetingMsg meetingMsg) {
        try {
            _webSocket.sendMsg(meetingMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================================================================================
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onWebSocketOpen() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (_connectEvent != null) {
                    _connectEvent.onSuccess();
                }
            }
        });
    }

    @Override
    public void onWebSocketOpenFailed(final String msg) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (_webSocket != null && !_webSocket.isOpen()) {
                    _connectEvent.onFailed(msg);
                } else {
                    if (_peerHelper != null) {
                        _peerHelper.exitRoom();
                    }
                }
            }
        });
    }

    @Override
    public void onJoinToRoom(final ArrayList<String> connections, final String myId) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (_peerHelper != null) {
                    _peerHelper.onJoinToRoom(connections, myId, _videoEnable, _mediaType);
                    if (_mediaType == MediaType.TYPE_VIDEO || _mediaType == MediaType.TYPE_MEETING) {
                        toggleSpeaker(true);
                    }
                }
            }
        });
    }

    @Override
    public void onRemoteJoinToRoom(final String socketId) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (_peerHelper != null) {
                    _peerHelper.onRemoteJoinToRoom(socketId);

                }
            }
        });
    }

    @Override
    public void onRemoteIceCandidate(final String socketId, final IceCandidate iceCandidate) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (_peerHelper != null) {
                    _peerHelper.onRemoteIceCandidate(socketId, iceCandidate);
                }
            }
        });
    }

    @Override
    public void onRemoteIceCandidateRemove(final String socketId, final List<IceCandidate> iceCandidates) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (_peerHelper != null) {
                    _peerHelper.onRemoteIceCandidateRemove(socketId, iceCandidates);
                }
            }
        });
    }

    @Override
    public void onRemoteOutRoom(final String socketId) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (_peerHelper != null) {
                    _peerHelper.onRemoteOutRoom(socketId);
                }
            }
        });
    }

    @Override
    public void onReceiveOffer(final String socketId, final String sdp) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (_peerHelper != null) {
                    _peerHelper.onReceiveOffer(socketId, sdp);
                }
            }
        });
    }

    @Override
    public void onReceiverAnswer(final String socketId, final String sdp) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (_peerHelper != null) {
                    _peerHelper.onReceiverAnswer(socketId, sdp);
                }
            }
        });
    }

    @Override
    public void onReceiverMsg(final String msg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (_peerHelper != null) {
                    _peerHelper.onReceiverMsg(msg);
                }
            }
        });
    }

    @Override
    public void onReceiverOnlineList(final String message) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (_peerHelper != null) {
                    _peerHelper.onReceiverOnlineList(message);
                }
            }
        });
    }

    // ================================================================================
    public void toggleSpeaker(boolean enable) {
        if (_peerHelper != null) {
            _peerHelper.toggleSpeaker(enable);
        }
    }

    public void setMeetEvent(OnMeetEvent meetEvent) {
        if (_peerHelper != null) {
            _peerHelper.setMeetEvent(meetEvent);
        }
    }

    public void setCallback(IViewCallback callback) {
        if (_peerHelper != null) {
            _peerHelper.setViewCallback(callback);
        }
    }

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
}
