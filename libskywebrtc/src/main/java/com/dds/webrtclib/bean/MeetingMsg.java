package com.dds.webrtclib.bean;

/**
 * <pre>
 *  创建:  梁玉涛 2019/6/24 on 16:50
 *  项目: WebRTC2Android
 *  描述:
 *  更新:
 * <pre>
 */
public class MeetingMsg{
    //        {
//            "eventName":"NOTICE",
//                "serverFunc":"sayHello",
//                "clientFunc":"retHello",
//                "sendId":"tmp1351400047",
//                "sendName":"临时用户1351400047",
//                "chatType":"MEETING",
//                "msgType":"MEET_REQUEST",
//                "targetIds":"15285536455",
//                "data":{
//            "received":false,
//                    "msg":"",
//                    "roomId":"1576922050"
//        }
//        }
    private String eventName;
    private String serverFunc;
    private String clientFunc;

    private String eventId;

    private String sendId;
    private String sendName;

    private MsgType msgType; //
    private ChatType chatType;
    private String targetIds;
    private MeetingContent data;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getServerFunc() {
        return serverFunc;
    }

    public void setServerFunc(String serverFunc) {
        this.serverFunc = serverFunc;
    }

    public String getClientFunc() {
        return clientFunc;
    }

    public void setClientFunc(String clientFunc) {
        this.clientFunc = clientFunc;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getTargetIds() {
        return targetIds;
    }

    public void setTargetIds(String targetIds) {
        this.targetIds = targetIds;
    }

    public MeetingContent getData() {
        return data;
    }

    public void setData(MeetingContent data) {
        this.data = data;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public ChatType getChatType() {
        return chatType;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }
}
