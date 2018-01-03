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
        value = "RCD:ZXJImageText"
        , flag = MessageTag.ISCOUNTED
)
/**
 * 分享发送给好友的消息，都用RCD:ZXJImageText
 * 参数有title消息的标题，content消息的内容，limage消息的展示缩略图，
 * type类型（1景点，3文章游记，4出行活动，5美食商铺，6住宿商铺，7游玩商铺），
 * bindid对应数据的ID
 */
public class ShareMessage extends MessageContent {
    private static final String TAG = "PacketMessage";
    private String content;
    private String title;
    private String limage;
    private String type;
    private String bindid;
    private String userId;
    protected String extra;
    public static final Creator<ShareMessage> CREATOR = new Creator<ShareMessage>() {
        public ShareMessage createFromParcel(Parcel source) {
            return new ShareMessage(source);
        }

        public ShareMessage[] newArray(int size) {
            return new ShareMessage[size];
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
            jsonObj.put("title", title);
            jsonObj.put("limage", limage);
            jsonObj.put("type", type);
            jsonObj.put("bindid", bindid);
            jsonObj.put("userId", userId);
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


    protected ShareMessage(String userId,String content, String title, String limage, String type,String bindid) {
        setContent(content);
        setTitle(title);
        setLimage(limage);
        setType(type);
        setBindid(bindid);
        setUserId(userId);
    }


    public static ShareMessage obtain(String content, String title, String limage, String type,String bindid) {
        return new ShareMessage("",content, title, limage,type,bindid);
    }
    public static ShareMessage obtain(String userId,String content, String title, String limage, String type,String bindid) {
        return new ShareMessage(userId,content, title, limage,type,bindid);
    }

    public ShareMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);

            if (jsonObj.has("content"))
                content = jsonObj.optString("content");
            title = jsonObj.optString("title");
            limage = jsonObj.optString("limage");
            type = jsonObj.optString("type");
            bindid = jsonObj.optString("bindid");
            userId = jsonObj.optString("userId");

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
        ParcelUtils.writeToParcel(dest, this.getTitle());
        ParcelUtils.writeToParcel(dest, this.getLimage());
        ParcelUtils.writeToParcel(dest, this.getType());
        ParcelUtils.writeToParcel(dest, this.getBindid());
        ParcelUtils.writeToParcel(dest, this.getUserId());
    }


    public ShareMessage(Parcel in) {
        this.setExtra(ParcelUtils.readFromParcel(in));
        this.setContent(ParcelUtils.readFromParcel(in));
        this.setUserInfo((UserInfo) ParcelUtils.readFromParcel(in, UserInfo.class));
        this.setMentionedInfo((MentionedInfo) ParcelUtils.readFromParcel(in, MentionedInfo.class));
        setTitle(ParcelUtils.readFromParcel(in));
        setLimage(ParcelUtils.readFromParcel(in));
        setType(ParcelUtils.readFromParcel(in));
        setBindid(ParcelUtils.readFromParcel(in));
        setUserId(ParcelUtils.readFromParcel(in));
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLimage() {
        return limage;
    }

    public void setLimage(String limage) {
        this.limage = limage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBindid() {
        return bindid;
    }

    public void setBindid(String bindid) {
        this.bindid = bindid;
    }

    public ShareMessage(String content) {
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
