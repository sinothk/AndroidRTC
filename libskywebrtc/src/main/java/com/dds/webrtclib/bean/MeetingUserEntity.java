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

    public MeetingUserEntity() {
    }

    public MeetingUserEntity(String userId) {
        this.userId = userId;
    }

    //    private String cityName;
//    private String cityCode;

//    public String getCityName() {
//        return cityName;
//    }
//
//    public void setCityName(String cityName) {
//        this.cityName = cityName;
//    }
//
//    public String getCityCode() {
//        return cityCode;
//    }
//
//    public void setCityCode(String cityCode) {
//        this.cityCode = cityCode;
//    }

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
}
