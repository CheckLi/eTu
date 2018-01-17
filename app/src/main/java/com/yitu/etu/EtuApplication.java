package com.yitu.etu;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.view.View;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.autonavi.amap.mapcore.FileUtil;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yitu.etu.dialog.LoadingDialog;
import com.yitu.etu.entity.AppConstant;
import com.yitu.etu.entity.ChatToken;
import com.yitu.etu.entity.ObjectBaseEntity;
import com.yitu.etu.entity.UserInfo;
import com.yitu.etu.eventBusItem.EventClearSuccess;
import com.yitu.etu.eventBusItem.EventOpenRealTime;
import com.yitu.etu.eventBusItem.LoginSuccessEvent;
import com.yitu.etu.service.UpdateLocationService;
import com.yitu.etu.tools.GsonCallback;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.ui.activity.MapActivity;
import com.yitu.etu.ui.activity.SearchResultUserActivity;
import com.yitu.etu.ui.fragment.Chat.MyExtensionModule;
import com.yitu.etu.util.LogUtil;
import com.yitu.etu.util.PrefrersUtil;
import com.yitu.etu.util.TextUtils;
import com.yitu.etu.util.ToastUtil;
import com.yitu.etu.util.activityUtil;
import com.yitu.etu.util.imageLoad.ImageLoadUtil;
import com.yitu.etu.util.location.LocationUtil;
import com.yitu.etu.widget.chat.PacketMessage;
import com.yitu.etu.widget.chat.RealTimeLocationEndMessage;
import com.yitu.etu.widget.chat.RealTimeLocationMessageEndItem;
import com.yitu.etu.widget.chat.RedPacketMessageItem;
import com.yitu.etu.widget.chat.ShareImageMessageItem;
import com.yitu.etu.widget.chat.ShareMessage;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.LocationMessage;
import io.rong.message.TextMessage;
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
    public Poi myLocationPoi;//导航起点
    private  String APP_ID="wx0f5237d79cf12c32";
    public IWXAPI mIWXAPI;
    public static EtuApplication getInstance() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        readUserInfo();//读取个人登录信息
        if (isLogin()) {
            startUpLocationService();
        }
        /**
         * 异常代理
         */
        Thread.setDefaultUncaughtExceptionHandler(new CrashException());
        /**
         * 网络配置
         */
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(30000L, TimeUnit.MILLISECONDS)
                .readTimeout(30000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
        /**
         * 聊天初始化
         */
        initChat();

        share();
//        Picasso.with(this).setIndicatorsEnabled(true);
        initLocation();//获取定位信息
        initWeixinPay();//初始化微信支付
        /**
         * 初始图片
         */
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                ImageLoadUtil.getInstance().loadImage(imageView, path, 80, 80);
            }
        });
    }

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
        initWeixinPay();
    }

    /**
     * 初始化微信支付
     */
    public void initWeixinPay(){
        mIWXAPI= WXAPIFactory.createWXAPI(this,APP_ID,true);
        mIWXAPI.registerApp(APP_ID);
    }

    private void initChat() {
        RongIM.init(this);
        RongIM.getInstance().enableNewComingMessageIcon(true);//显示新消息提醒
        RongIM.getInstance().enableUnreadMessageIcon(true);//显示未读消息数目
        setMyExtensionModule();
        //注册平安符item
        RongIM.getInstance().registerMessageType(PacketMessage.class);
        RongIM.getInstance().registerMessageTemplate(new RedPacketMessageItem());
        //注册分享item
        RongIM.getInstance().registerMessageType(ShareMessage.class);
        RongIM.getInstance().registerMessageTemplate(new ShareImageMessageItem());
        //注册结束共享item
        RongIM.getInstance().registerMessageType(RealTimeLocationEndMessage.class);
        RongIM.getInstance().registerMessageTemplate(new RealTimeLocationMessageEndItem());
        /**
         * 设置会话界面操作的监听器。
         */
        RongIM.setConversationBehaviorListener(new RongIM.ConversationBehaviorListener() {
            @Override
            public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, io.rong.imlib.model.UserInfo userInfo) {
                Bundle bundle = new Bundle();
                bundle.putString("user_id", userInfo.getUserId());
                activityUtil.nextActivity(context, SearchResultUserActivity.class, bundle, false);
                return true;
            }

            @Override
            public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, io.rong.imlib.model.UserInfo userInfo) {
                return false;
            }

            @Override
            public boolean onMessageClick(final Context context, View view, final Message message) {
                if (message.getContent() instanceof LocationMessage) {
                    LocationMessage message1 = (LocationMessage) message.getContent();
//                    Poi end = new Poi(message1.getPoi(), new LatLng(message1.getLat(), message1.getLng()), "");
                    Bundle bundle=new Bundle();
                    bundle.putString("address",message1.getPoi());
                    bundle.putParcelable("data",new LatLng(message1.getLat(), message1.getLng()));
                    activityUtil.nextActivity(context, MapActivity.class,bundle,false);
//                    Tools.navi(context, end);
                    return true;
                    //如果是接收方并且是共享位置消息，需要跳转到自己的共享页
                } else if (message.getContent() instanceof TextMessage && ((TextMessage) message.getContent()).getExtra().equals("RCZXJRLMap") && message.getMessageDirection() == Message.MessageDirection.RECEIVE) {
                    final Activity activity= (Activity) context;
                    if(message.getConversationType()== Conversation.ConversationType.PRIVATE) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("target_id", message.getSenderUserId());
                        final LoadingDialog dialog = new LoadingDialog(context, "加入中...");
                        dialog.show();
                        Http.post(Urls.URL_GET_CHAT_ID, map, new GsonCallback<ObjectBaseEntity<Object>>() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                ToastUtil.showMessage("共享已结束");
                                dialog.hideDialog();
                            }

                            @Override
                            public void onResponse(ObjectBaseEntity<Object> response, int id) {
                                dialog.hideDialog();
                                if (response.success()) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("chat_id", response.data.toString());
                                    bundle.putString("title",activity.getIntent().getData().getQueryParameter("title"));
                                    bundle.putSerializable("type", Message.MessageDirection.RECEIVE);
                                    bundle.putSerializable("chatType", message.getConversationType());
//                                    activityUtil.nextActivity(context, AMapRealTimeActivity.class, bundle, false);
                                    sendRealTime(bundle);
                                } else {
                                    ToastUtil.showMessage(response.getMessage());
                                }
                            }
                        });
                    }else {
                        Bundle bundle = new Bundle();
                        bundle.putString("chat_id", message.getTargetId());
                        bundle.putSerializable("type", Message.MessageDirection.RECEIVE);
                        bundle.putString("title",activity.getIntent().getData().getQueryParameter("title"));
                        bundle.putSerializable("chatType", message.getConversationType());
//                        activityUtil.nextActivity(context, AMapRealTimeActivity.class, bundle, false);
                        sendRealTime(bundle);
                    }
                    return true;
                }
                return false;
            }

            @Override
            public boolean onMessageLinkClick(Context context, String s) {
                return false;
            }

            @Override
            public boolean onMessageLongClick(Context context, View view, Message message) {
                return false;
            }
        });


    }

    /**
     * 发送加入申请
     * @param bundle
     */
    private void sendRealTime(Bundle bundle){
        EventBus.getDefault().post(new EventOpenRealTime(bundle,true));
    }

    /**
     * 初始化自定义面板
     */
    public void setMyExtensionModule() {
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new MyExtensionModule());
            }
        }
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
        PlatformConfig.setWeixin("wx0f5237d79cf12c32", "de07142da052f2467dbf501b0aecbde8");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("642370776", "18b581dfed58148df4192595a236fa12", "http://91eto.com");
    }

    /**
     * 判断登录用
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
        startUpLocationService();
        if (userInfo != null && userInfo.getId() != 0 && !TextUtils.isEmpty(userInfo.getToken())) {
            this.userInfo = userInfo;
            //存储登录信息，第二次进入直接读取
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
     * 退出登录
     */
    public void loginOut() {
        stopUpLocationService();
        setUserInfo(null);
        setChatToken(null);
        PrefrersUtil.getInstance().remove(AppConstant.PARAM_SAVE_USER_INFO);
        PrefrersUtil.getInstance().remove(AppConstant.PARAM_SAVE_CHAT_TOKEN);
        PrefrersUtil.getInstance().remove(AppConstant.PARAM_SAVE_BUY_CAR);
        EventBus.getDefault().post(new LoginSuccessEvent(null));
        RongIM.getInstance().logout();
        RongIM.getInstance().disconnect();
       /* RongIM.getInstance().clearConversations(
                new RongIMClient.ResultCallback() {
                    @Override
                    public void onSuccess(Object o) {

                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                }, Conversation.ConversationType.PRIVATE, Conversation.ConversationType.GROUP, Conversation.ConversationType.SYSTEM,
                Conversation.ConversationType.APP_PUBLIC_SERVICE, Conversation.ConversationType.PUSH_SERVICE, Conversation.ConversationType.DISCUSSION,
                Conversation.ConversationType.CUSTOMER_SERVICE, Conversation.ConversationType.CHATROOM);*/
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
        RongIM.connect(chatToken, null);
        /**
         * 设置用户信者，供 RongIM 调用获取用户名称和头像信息。
         *
         * @param userInfoProvider 用户信息提供者。
         * @param isCacheUserInfo  设置是否由 IMKit 来缓存用户信息。<br>
         *                         如果 App 提供的 UserInfoProvider
         *                         每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
         *                         此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
         * @see UserInfoProvider
         */
       /* RongIM.setUserInfoProvider({ userId ->
            UserInfo(userId,"小王子", Uri.parse("http://rongcloud-web.qiniudn.com/docs_demo_rongcloud_logo.png"))
        }, true)*/
        /**
         * 设置用户信息的提供者，供 RongIM 调用获取用户名称和头像信息。
         *
         * @param userInfoProvider 用户信息提供者。
         * @param isCacheUserInfo  设置是否由 IMKit 来缓存用户信息。<br>
         *                         如果 App 提供的 UserInfoProvider
         *                         每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
         *                         此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
         * @see UserInfoProvider
         */
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public io.rong.imlib.model.UserInfo getUserInfo(final String s) {

                HashMap<String, String> map = new HashMap<>();
                map.put("user_id", s);
                Http.post(Urls.URL_GET_ID_USER_INFO, map, new GsonCallback<ObjectBaseEntity<UserInfo>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtil.e(e.getLocalizedMessage());

                    }

                    @Override
                    public void onResponse(ObjectBaseEntity<UserInfo> response, int id) {
                        if (response.success()) {
                            UserInfo info = response.data;
                            String url=Urls.address + info.getHeader();
                            if(url.startsWith(Urls.address+"http")){
                                url=url.replace(Urls.address,"");
                            }
                            io.rong.imlib.model.UserInfo userInfo = new io.rong.imlib.model.UserInfo(String.valueOf(info.getId()), info.getName(), Uri.parse(url));
                            RongIM.getInstance().refreshUserInfoCache(userInfo);
                        }
                    }
                });
                return null;
            }
        }, true);
    }

    public void stopUpLocationService() {
        if (service != null) {
            stopService(service);
            service = null;
        }
    }

    Intent service;

    public void startUpLocationService() {
        if (service == null) {
            service = new Intent(getApplicationContext(), UpdateLocationService.class);
            startService(service);
        }
    }
}
