package com.dds.webrtclib.bean;

/**
 * <pre>
 *  创建:  梁玉涛 2019/7/18 on 16:57
 *  项目: AndroidRTCLib
 *  描述:
 *  更新:
 * <pre>
 */
public class MeetingUserEntity {

    private String userId;
    private String userName;
    private String userPhoto;
    private boolean currLiving;

    public MeetingUserEntity() {
    }

    public MeetingUserEntity(String userId) {
        this.userId = userId;
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

    public boolean isCurrLiving() {
        return currLiving;
    }

    public void setCurrLiving(boolean currLiving) {
        this.currLiving = currLiving;
    }
}
