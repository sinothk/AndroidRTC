package com.dds.webrtclib;

import com.dds.webrtclib.bean.MeetingMsg;

import org.webrtc.MediaStream;

/**
 * Created by dds on 2017/10/23.
 */

public interface IViewCallback {

    void onSetLocalStream(MediaStream stream, String socketId);

    void onAddRemoteStream(MediaStream stream, String socketId);

    void onCloseWithId(String socketId);

    void onReceiverMsg(MeetingMsg msg);

    @Deprecated
    void onReceiverOnlineList(MeetingMsg meetingMsg);
}
