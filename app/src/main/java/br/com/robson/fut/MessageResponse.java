package br.com.robson.fut;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Objects;

/**
 * Created by Robson on 02/10/2016.
 */

public class MessageResponse {

    private String result;
    private MessageCodeEnum messageCodeEnum;
    private Bitmap bitmap;
    private List<Object> list;

    public MessageResponse(String result, MessageCodeEnum messageCodeEnum) {
        this.setResult(result);
        this.setMessageCodeEnum(messageCodeEnum);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public MessageCodeEnum getMessageCodeEnum() {
        return messageCodeEnum;
    }

    public void setMessageCodeEnum(MessageCodeEnum messageCodeEnum) {
        this.messageCodeEnum = messageCodeEnum;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }
}
