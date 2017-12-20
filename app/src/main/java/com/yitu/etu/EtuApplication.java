package com.yitu.etu;

import android.app.Application;

import com.autonavi.amap.mapcore.FileUtil;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yitu.etu.entity.AppConstant;
import com.yitu.etu.entity.UserInfo;
import com.yitu.etu.eventBusItem.EventClearSuccess;
import com.yitu.etu.eventBusItem.LoginSuccessEvent;
import com.yitu.etu.util.PrefrersUtil;
import com.yitu.etu.util.TextUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;

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
    private UserInfo userInfo;

    public static EtuApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        readUserInfo();//读取个人登陆信息
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
        Picasso.with(this).setIndicatorsEnabled(true);
    }

    /**
     * 分享参数配置
     */
    public void share() {
        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }

    /**
     * 判断登陆用
     *
     * @return
     */
    public boolean isLogin() {
        if (userInfo != null && userInfo.getId() != 0 && !TextUtils.isEmpty(userInfo.getToken())) {
            return true;
        } else {
            return false;
        }
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * 存储个人信息
     *
     * @param userInfo
     */
    public void setUserInfo(UserInfo userInfo) {
        if (userInfo != null && userInfo.getId() != 0 && !TextUtils.isEmpty(userInfo.getToken())) {
            this.userInfo = userInfo;
            //存储登陆信息，第二次进入直接读取
            PrefrersUtil.getInstance().saveClass(AppConstant.PARAM_SAVE_USER_INFO, userInfo);
        } else {
            this.userInfo = null;
        }
    }

    /**
     * 从本地读取个人数据
     */
    public void readUserInfo() {
        this.userInfo = PrefrersUtil.getInstance().getClass(AppConstant.PARAM_SAVE_USER_INFO, UserInfo.class);
    }

    /**
     * 退出登陆
     */
    public void loginOut() {
        setUserInfo(null);
        PrefrersUtil.getInstance().remove(AppConstant.PARAM_SAVE_USER_INFO);
        EventBus.getDefault().post(new LoginSuccessEvent(null));
        RongIM.getInstance().logout();
    }

    /**
     * 清理缓存
     */
    public void clearCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Picasso.with(getApplicationContext()).invalidate(getCacheDir());
                    Glide.get(getApplicationContext()).clearDiskCache();
                    FileUtil.deleteFile(getCacheDir());
                    EventBus.getDefault().post(new EventClearSuccess(true));
                } catch (Exception e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new EventClearSuccess(false));
                }
            }
        }).start();
    }
}
