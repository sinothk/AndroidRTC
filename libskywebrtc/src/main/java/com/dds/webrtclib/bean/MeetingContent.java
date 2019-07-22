package com.dds.webrtclib.bean;

import java.util.ArrayList;

/**
 * <pre>
 *  创建:  梁玉涛 2019/6/24 on 16:50
 *  项目: WebRTC2Android
 *  描述:
 *  更新:
 * <pre>
 */
public class MeetingContent {

    private String id;
    private String name;
    private String photo;

    private String userId;
    private String userName;
    private String userPhoto;

    //
    private String msg;

    // room
    private String roomId;
    private String roomName;
    private String room;

    private ArrayList<MeetingUserEntity> roomClients;

    private boolean received;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public ArrayList<MeetingUserEntity> getRoomClients() {
        return roomClients;
    }

    public void setRoomClients(ArrayList<MeetingUserEntity> roomClients) {
        this.roomClients = roomClients;
    }
}
