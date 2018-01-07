package com.yitu.etu.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @className:EmojiGroupBean
 * @description:
 * @author: JIAMING.LI
 * @date:2018年01月07日 17:55
 */
public class EmojiChildBean implements Serializable{
    private String name;
    private int id;
    private List<String> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
