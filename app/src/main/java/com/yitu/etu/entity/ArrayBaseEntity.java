package com.yitu.etu.entity;

import java.util.List;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/19.
 */
public class ArrayBaseEntity <T>{
    public int status;
    public List<T> data;
    public String message;
    public String time;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
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
