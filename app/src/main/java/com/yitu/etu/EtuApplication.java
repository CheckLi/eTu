package com.yitu.etu;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yitu.etu.entity.UserInfoEntity;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import io.rong.imkit.RongIM;
import okhttp3.OkHttpClient;

/**
 * @className:EtuApplocation
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月08日 16:25
 */
public class EtuApplication extends Application {
    private static EtuApplication mInstance;
    private static UserInfoEntity userInfo;

    public static UserInfoEntity getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(UserInfoEntity userInfo) {
        EtuApplication.userInfo = userInfo;
    }

    public static EtuApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        /**
         * 异常代理
         */
        Thread.setDefaultUncaughtExceptionHandler(new CrashException());
        /**
         * 网络配置
         */
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
        /**
         * 聊天初始化
         */
        RongIM.init(this);
        RongIM.getInstance().enableNewComingMessageIcon(true);//显示新消息提醒
        RongIM.getInstance().enableUnreadMessageIcon(true);//显示未读消息数目
        /*RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                switch (connectionStatus){

                    case CONNECTED://连接成功。
                        ToastUtil.showMessage("连接成功");
                        break;
                    case DISCONNECTED://断开连接。
                        ToastUtil.showMessage("断开连接");
                        break;
                    case CONNECTING://连接中。

                        break;
                    case NETWORK_UNAVAILABLE://网络不可用。

                        break;
                    case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线

                        break;
                }
            }
        });*/

        share();
    }

    /**
     * 分享参数配置
     */
    public void share(){
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }
}
