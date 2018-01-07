package com.yitu.etu.util;

import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.yitu.etu.EtuApplication;
import com.yitu.etu.dialog.LoadingDialog;
import com.yitu.etu.entity.ObjectBaseEntity;
import com.yitu.etu.entity.UserInfo;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.MyActivityManager;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.ui.activity.LoginActivity;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * @className:LoginUtil
 * @description:第三方登陆类
 * @author: JIAMING.LI
 * @date:2018年01月07日 12:32
 */
public class LoginUtil implements UMAuthListener {
    public final static int TYPE_LOGIN_WEIXIN = 0;
    public final static int TYPE_LOGIN_WEIBO = 1;
    private int type = TYPE_LOGIN_WEIXIN;
    private static LoginUtil mInstance;
    private Context mContext;
    private LoadingDialog mDialog;

    public LoginUtil(Context context) {
        mContext = context;
        mDialog = new LoadingDialog(mContext, "登陆中...");
    }

    public static LoginUtil getInstance(Context mContext) {
        if (mInstance == null) {
            mInstance = new LoginUtil(mContext);
        }
        return mInstance;
    }

    public void startLogin(int type) {
        this.type = type;
        switch (type) {
            case 0:
                UMShareAPI.get(mContext).getPlatformInfo((Activity) mContext, SHARE_MEDIA.WEIXIN, this);
                break;
            case 1:
                UMShareAPI.get(mContext).getPlatformInfo((Activity) mContext, SHARE_MEDIA.SINA, this);
                break;

            default:
                break;
        }

    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        mDialog.showDialog();
    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//        mDialog.hideDialog();
        switch (type) {
            case TYPE_LOGIN_WEIXIN:
                loginServer(Urls.URL_LOGIN_WEIXIN, map.get("uid"), map.get("name"),map.get("iconurl"), map.get("gender") );
                break;
            case TYPE_LOGIN_WEIBO:
                loginServer(Urls.URL_LOGIN_WEIBO, map.get("uid"), map.get("name"), map.get("iconurl"), map.get("gender"));
                break;

            default:
                break;
        }
    }

    @Override
    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
        mDialog.hideDialog();
        ToastUtil.showMessage("登陆失败");
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media, int i) {
        mDialog.hideDialog();
        ToastUtil.showMessage("取消登陆");
    }


    private void loginServer(String url, String openid, final String name, String header, String sex) {
        HashMap<String, String> params = new HashMap<>();
        params.put("openid", openid);
        params.put("name", name);
        params.put("header", header);
        params.put("sex", sex);
        Http.post(url, params, new GsonCallback<ObjectBaseEntity<UserInfo>>() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showMessage("登陆失败");
                mDialog.hideDialog();
            }

            @Override
            public void onResponse(ObjectBaseEntity<UserInfo> response, int id) {
                mDialog.hideDialog();
                if (response.success()) {
//                    PrefrersUtil.getInstance().saveValue(AppConstant.PARAM_SAVE_USERNAME, name);
                    EtuApplication.getInstance().setUserInfo(response.data);
                    EtuApplication.getInstance().connectChat();//登陆聊天服务器
                    MyActivityManager.getInstance().finishActivity(LoginActivity.class);
                } else {
                    ToastUtil.showMessage(response.getMessage());
                }
            }
        });
    }

    public void onDestory() {
        mContext = null;
        mDialog = null;
        mInstance = null;
    }
}
