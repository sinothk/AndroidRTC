package com.dds.webrtclib.bean;

import java.util.HashMap;

/**
 * <pre>
 *  创建:  梁玉涛 2019/7/19 on 9:58
 *  项目: AndroidRTCLib
 *  描述:
 *  更新:
 * <pre>
 */
public class DataCache {

    private static HashMap<String, MeetingContent> onlineUserInfoMap = null;

    public static void putOnlineUserInfo(String userId, MeetingContent content) {
        if (onlineUserInfoMap == null) {
            onlineUserInfoMap = new HashMap<>();
        }
        onlineUserInfoMap.put(userId, content);
    }

    public static void clearOnlineUserInfo() {
        if (onlineUserInfoMap == null) {
            return;
        }
        onlineUserInfoMap.clear();
    }

    public static MeetingContent getOnlineUserInfo(String key) {
        if (onlineUserInfoMap == null) {
            return new MeetingContent();
        }
        return onlineUserInfoMap.get(key);
    }
}
