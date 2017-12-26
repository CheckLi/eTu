package com.yitu.etu.entity;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/26.
 */
public class HttpStateEntity {

    public int status;
    public String message;
    public String time;

    public boolean success() {
        return status == 1 ? true : false;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
