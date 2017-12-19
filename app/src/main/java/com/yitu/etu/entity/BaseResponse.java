package com.yitu.etu.entity;

/**
 * @className:BaseResponse
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月19日 10:44
 */
public class BaseResponse<T> {
    private int status;
    private T data;
    private String message;
    private String time;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
