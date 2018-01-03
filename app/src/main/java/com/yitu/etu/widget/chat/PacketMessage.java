package com.yitu.etu.widget.chat;

import android.os.Parcel;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MentionedInfo;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

/**
 * @className:PacketMessage
 * @description:
 * @author: JIAMING.LI
 * @date:2018年01月01日 15:05
 */
@MessageTag(
        value = "RCD:ZXJPacket"
        , flag = MessageTag.ISCOUNTED
)
/**
 * 平安符红包的objectname是RCD:ZXJPacket  参数有content平安符标题，count平安符个数，people可抢的人数
 */
public class PacketMessage extends MessageContent {
    private static final String TAG = "PacketMessage";
    private String content;
    private String pinanCount;
    private String people;
    private String pinId;
    protected String extra;
    public static final Creator<PacketMessage> CREATOR = new Creator<PacketMessage>() {
        public PacketMessage createFromParcel(Parcel source) {
            return new PacketMessage(source);
        }

        public PacketMessage[] newArray(int size) {
            return new PacketMessage[size];
        }
    };

    public String getExtra() {
        return this.extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("content", content);
            jsonObj.put("count", pinanCount);
            jsonObj.put("people", people);
            jsonObj.put("id", pinId);
            if (!TextUtils.isEmpty(this.getExtra())) {
                jsonObj.put("extra", this.getExtra());
            }

            if (this.getJSONUserInfo() != null) {
                jsonObj.putOpt("user", this.getJSONUserInfo());
            }
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }


    protected PacketMessage(String content, String pinanCount, String people,String pinId) {
        setContent(content);
        setPinanCount(pinanCount);
        setPeople(people);
        setPinId(pinId);

    }

    public String getPinId() {
        return pinId;
    }

    public void setPinId(String pinId) {
        this.pinId = pinId;
    }

    public static PacketMessage obtain(String content, String pinanCount, String people, String pinId) {
        return new PacketMessage(content, pinanCount, people,pinId);
    }

    public PacketMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("content"))
                content = jsonObj.optString("content");
            pinanCount = jsonObj.optString("count");
            people = jsonObj.optString("people");
            pinId = jsonObj.optString("id");

            if (jsonObj.has("extra")) {
                this.setExtra(jsonObj.optString("extra"));
            }

            if (jsonObj.has("user")) {
                this.setUserInfo(this.parseJsonToUserInfo(jsonObj.getJSONObject("user")));
            }
        } catch (JSONException e) {

        }

    }

    public void setContent(String content) {
        this.content = content;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, this.getExtra());
        ParcelUtils.writeToParcel(dest, this.content);
        ParcelUtils.writeToParcel(dest, this.getUserInfo());
        ParcelUtils.writeToParcel(dest, this.getMentionedInfo());
        ParcelUtils.writeToParcel(dest, this.getPinanCount());
        ParcelUtils.writeToParcel(dest, this.getPeople());
        ParcelUtils.writeToParcel(dest, this.getPinId());
    }


    public String getPinanCount() {
        return pinanCount;
    }

    public void setPinanCount(String pinanCount) {
        this.pinanCount = pinanCount;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public PacketMessage(Parcel in) {
        this.setExtra(ParcelUtils.readFromParcel(in));
        this.setContent(ParcelUtils.readFromParcel(in));
        this.setUserInfo((UserInfo) ParcelUtils.readFromParcel(in, UserInfo.class));
        this.setMentionedInfo((MentionedInfo) ParcelUtils.readFromParcel(in, MentionedInfo.class));
        setPinanCount(ParcelUtils.readFromParcel(in));
        setPeople(ParcelUtils.readFromParcel(in));
        setPinId(ParcelUtils.readFromParcel(in));
    }

    public PacketMessage(String content) {
        this.setContent(content);
    }

    public String getContent() {
        return this.content;
    }

    public List<String> getSearchableWord() {
        List<String> words = new ArrayList();
        words.add(this.content);
        return words;
    }
}
