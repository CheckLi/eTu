package com.yitu.etu.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @className:EmojiGroupBean
 * @description:
 * @author: JIAMING.LI
 * @date:2018年01月07日 17:55
 */
public class EmojiChildBean  implements Parcelable{
    private String name;
    private int id;
    private List<String> list;

    public EmojiChildBean() {
    }

    protected EmojiChildBean(Parcel in) {
        name = in.readString();
        id = in.readInt();
        list = in.createStringArrayList();
    }

    public static final Creator<EmojiChildBean> CREATOR = new Creator<EmojiChildBean>() {
        @Override
        public EmojiChildBean createFromParcel(Parcel in) {
            return new EmojiChildBean(in);
        }

        @Override
        public EmojiChildBean[] newArray(int size) {
            return new EmojiChildBean[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeStringList(list);
    }
}
