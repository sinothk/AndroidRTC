package com.dds.webrtclib.bean;

/**
 * <pre>
 *  创建:  梁玉涛 2019/6/24 on 16:50
 *  项目: WebRTC2Android
 *  描述:
 *  更新:
 * <pre>
 */
public class MeetingMsg<T> {
    /**
     * eventName : NOTICE
     * serverFunc : sayHello
     * clientFunc : retHello
     * sendId : phone.value
     * targetIds : targetIds.value
     * data : {"msg":"message"}
     */

    private String eventName;
    private String serverFunc;
    private String clientFunc;

    private String eventId;
    private MsgType msgType; //
    private String sendId;
    private String targetIds;
    private ChatType chatType;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
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
}
