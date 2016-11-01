package br.com.robson.fut;

/**
 * Created by Robson on 02/10/2016.
 */

public class MessageRequest {

    private String url;
    private MessageCodeEnum messageCodeEnum;
    private boolean NotConvertToString;

    public MessageRequest(String url, MessageCodeEnum messageCodeEnum) {
        this.url = url;
        this.messageCodeEnum = messageCodeEnum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MessageCodeEnum getMessageCodeEnum() {
        return messageCodeEnum;
    }

    public void setMessageCodeEnum(MessageCodeEnum messageCodeEnum) {
        this.messageCodeEnum = messageCodeEnum;
    }

    public boolean isNotConvertToString() {
        return NotConvertToString;
    }

    public void setNotConvertToString(boolean notConvertToString) {
        NotConvertToString = notConvertToString;
    }
}
