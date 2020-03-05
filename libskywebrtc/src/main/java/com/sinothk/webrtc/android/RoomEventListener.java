package com.sinothk.webrtc.android;

import java.util.ArrayList;

public interface RoomEventListener {

    void onSuccess(ArrayList<String> connections, String myId);

    void onFails(String myId);
}
