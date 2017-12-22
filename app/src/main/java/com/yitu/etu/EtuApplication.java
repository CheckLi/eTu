package com.yitu.etu;

import android.app.Application;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.autonavi.amap.mapcore.FileUtil;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yitu.etu.entity.AppConstant;
import com.yitu.etu.entity.ObjectBaseEntity;
import com.yitu.etu.entity.UserInfo;
import com.yitu.etu.entity.ChatToken;
import com.yitu.etu.eventBusItem.EventClearSuccess;
import com.yitu.etu.eventBusItem.LoginSuccessEvent;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.util.LogUtil;
import com.yitu.etu.util.PrefrersUtil;
import com.yitu.etu.util.TextUtils;
import com.yitu.etu.util.location.LocationUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.Call;
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
    private String chatToken;
    private AMapLocation mLocation;

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
        initLocation();//获取定位信息
    }

    public void initLocation() {
        LocationUtil.getInstance().startLocation(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                mLocation = aMapLocation;
            }
        });
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
            EventBus.getDefault().post(new LoginSuccessEvent(this.userInfo));
        } else {
            this.userInfo = null;
        }
    }

    /**
     * 从本地读取个人数据
     */
    public void readUserInfo() {
        this.userInfo = PrefrersUtil.getInstance().getClass(AppConstant.PARAM_SAVE_USER_INFO, UserInfo.class);
        this.chatToken = PrefrersUtil.getInstance().getValue(AppConstant.PARAM_SAVE_CHAT_TOKEN, "");
    }

    /**
     * 退出登陆
     */
    public void loginOut() {
        setUserInfo(null);
        setChatToken(null);
        PrefrersUtil.getInstance().remove(AppConstant.PARAM_SAVE_USER_INFO);
        PrefrersUtil.getInstance().remove(AppConstant.PARAM_SAVE_CHAT_TOKEN);
        PrefrersUtil.getInstance().remove(AppConstant.PARAM_SAVE_BUY_CAR);
        EventBus.getDefault().post(new LoginSuccessEvent(null));
        RongIM.getInstance().logout();
    }

    public String getChatToken() {
        return chatToken;
    }

    public void setChatToken(String chatToken) {
        this.chatToken = chatToken;
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

    public AMapLocation getLocation() {
        return mLocation;
    }

    public void setLocation(AMapLocation location) {
        mLocation = location;
    }

    /**
     * 链接聊天服务器
     */
    public void connectChat() {
        if (isLogin()) {
            if (!TextUtils.isEmpty(chatToken)) {
                connect();
            } else {
                Http.post(Urls.chatToken, new HashMap<String, String>(), new GsonCallback<ObjectBaseEntity<ChatToken>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(ObjectBaseEntity<ChatToken> response, int id) {
                        if (response.success()) {
                            setChatToken(response.getData().getToken());
                            PrefrersUtil.getInstance().saveValue(AppConstant.PARAM_SAVE_CHAT_TOKEN, chatToken);
                            connect();
                        }
                    }
                });
            }
        }
    }

    private void connect() {
        RongIM.connect(chatToken, new RongIMClient.ConnectCallback() {
            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {
                LogUtil.e("chatConnect", "Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token\n" +
                        "             * 2.  token 对应的 appKey 和工程里设置的 appKey 是否一致");
            }

            @Override
            public void onSuccess(String s) {
                LogUtil.e("chatConnect", "聊天链接成功");
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtil.e("chatConnect", "聊天链接失败" + errorCode.getMessage() + " " + errorCode.getValue());
            }
        });
    }
}
