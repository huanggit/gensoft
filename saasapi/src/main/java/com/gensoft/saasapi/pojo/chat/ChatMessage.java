package com.gensoft.saasapi.pojo.chat;

/**
 * Created by alan on 16-7-13.
 */
public class ChatMessage {

    private Integer isVoice;
    private String content;
    private Long receiverId;

    public Integer getIsVoice() {
        return isVoice;
    }

    public void setIsVoice(Integer isVoice) {
        this.isVoice = isVoice;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
}
