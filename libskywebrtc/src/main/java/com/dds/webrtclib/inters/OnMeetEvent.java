package com.dds.webrtclib.inters;

import com.dds.webrtclib.bean.MeetingMsg;

/**
 * <pre>
 *  创建:  梁玉涛 2019/7/22 on 13:56
 *  项目: AndroidRTCLib
 *  描述:
 *  更新:
 * <pre>
 */
public interface OnMeetEvent {
    void onEnterOrExitRoom(MeetingMsg meetingMsg);
}
