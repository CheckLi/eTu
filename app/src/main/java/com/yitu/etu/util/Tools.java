package com.yitu.etu.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.yitu.etu.EtuApplication;
import com.yitu.etu.R;
import com.yitu.etu.dialog.SingleTipsDialog;
import com.yitu.etu.tools.Http;
import com.yitu.etu.tools.Urls;
import com.yitu.etu.ui.activity.BaseActivity;
import com.yitu.etu.ui.activity.FriendListActivity;
import com.yitu.etu.ui.activity.ImageShowActivity;
import com.yitu.etu.widget.chat.ShareMessage;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;
import io.rong.message.TextMessage;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okhttp3.Call;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/21.
 */
public class Tools {
    public static int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static void showImage(Context context, String path, ImageView imageView) {
        Intent intent = new Intent(context, ImageShowActivity.class);
        intent.putExtra("path", path);//非必须
        int[] location = new int[2];
        imageView.getLocationOnScreen(location);
        intent.putExtra("locationX", location[0]);//必须
        intent.putExtra("locationY", location[1]);//必须

        intent.putExtra("width", imageView.getWidth());//必须
        intent.putExtra("height", imageView.getHeight());//必须
        ((BaseActivity) context).overridePendingTransition(0, 0);
        context.startActivity(intent);
    }

    public static PopupWindow getPopupWindow(Context context, String[] strings, final AdapterView.OnItemClickListener onItemClick, String bgType) {
        ArrayList list = new ArrayList();

        TextPaint newPaint = new TextPaint();
        float textSize = context.getResources().getDisplayMetrics().scaledDensity * 18;
        newPaint.setTextSize(textSize);
        int maxLength = 0;
        for (String str :
                strings) {
            Map<String, String> map = new HashMap<>();
            map.put("data", str);
            maxLength = Math.max((int) newPaint.measureText(str), maxLength);
            list.add(map);
        }
        final PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_pop, null);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        if ("left".equals(bgType)) {
            view.setBackgroundResource(R.drawable.bg_left_pop);
        } else {
            view.setBackgroundResource(R.drawable.bg1111);
        }
        listView.setLayoutParams(new LinearLayout.LayoutParams(maxLength + dp2px(context, 3), LinearLayout.LayoutParams.WRAP_CONTENT));
        listView.setAdapter(new SimpleAdapter(context, list, R.layout.item_pop, new String[]{"data"}, new int[]{R.id.text}));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemClick.onItemClick(parent, view, position, id);
                popupWindow.dismiss();
            }
        });
        popupWindow.setContentView(view);
        return popupWindow;

    }

    public static void navi(Context context, Poi end) {
        if (EtuApplication.getInstance().myLocationPoi == null) {
            ToastUtil.showMessage("没有获取到你的位置信息");
            return;
        }
        AmapNaviPage.getInstance().showRouteActivity(context, new AmapNaviParams(EtuApplication.getInstance().myLocationPoi, null, end, AmapNaviType.DRIVER), new INaviInfoCallback() {
            @Override
            public void onInitNaviFailure() {

            }

            @Override
            public void onGetNavigationText(String s) {

            }

            @Override
            public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

            }

            @Override
            public void onArriveDestination(boolean b) {

            }

            @Override
            public void onStartNavi(int i) {

            }

            @Override
            public void onCalculateRouteSuccess(int[] ints) {

            }

            @Override
            public void onCalculateRouteFailure(int i) {

            }

            @Override
            public void onStopSpeaking() {

            }
        });
    }

    /**
     * 添加好友
     *
     * @param activity
     * @param id
     */
    public static void addFriend(final BaseActivity activity, String id) {
        activity.showWaitDialog("添加中...");
        HashMap<String, String> params = new HashMap<>();
        params.put("suser_id", id);
        Http.post(Urls.URL_ADD_FRIEND, params, new StringCallback() {


            @Override
            public void onError(Call call, Exception e, int id) {
                activity.hideWaitDialog();
                activity.showToast("添加失败");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    activity.hideWaitDialog();
                    activity.showToast(jsonObject.optString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        });
    }

    /**
     * 开始聊天
     *
     * @param title
     * @param id
     * @param context
     */
    public static void startChat(String title, String id, Context context) {
        RongIM.getInstance().startPrivateChat(context, id, title);
    }

    /**
     * 开始聊天
     *
     * @param title
     * @param id
     * @param context
     */
    public static void startChat(String title, String id, String chatContent, Context context) {
        RongIM.getInstance().startPrivateChat(context, id, title);
        if(TextUtils.isEmpty(chatContent)){
            return;
        }
        // 构造 TextMessage 实例
        TextMessage myTextMessage = TextMessage.obtain(chatContent);

        /* 生成 Message 对象。
         * "7127" 为目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
         * Conversation.ConversationType.PRIVATE 为私聊会话类型，根据需要，也可以传入其它会话类型，如群组，讨论组等。
         */
        Message myMessage = Message.obtain(id, Conversation.ConversationType.PRIVATE, myTextMessage);
        /**
         * <p>发送消息。
         * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}
         * 中的方法回调发送的消息状态及消息体。</p>
         *
         * @param message     将要发送的消息体。
         * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
         *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
         *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
         * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
         * @param callback    发送消息的回调，参考 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}。
         */
        RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                //消息本地数据库存储成功的回调
            }

            @Override
            public void onSuccess(Message message) {
                //消息通过网络发送成功的回调
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                //消息发送失败的回调
            }
        });
    }

    /**
     * 讨论组聊天
     */
    public static void startChatGroup(final Context context, final List<String> targetUserIds, final String title) {
        RongIM.getInstance().createDiscussionChat(context, targetUserIds, title);
    }

    public static void createChagGroup(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("select", true);
        activityUtil.nextActivity(context, FriendListActivity.class, bundle, false);
    }

    /**
     * 开始群聊
     * @param context
     */
    public static void startGroupChat(Context context,String groupId,String title) {
       RongIM.getInstance().startDiscussionChat(context,groupId,title);
    }

    public static void sendMessage(ShareMessage msg, final Activity activity) {
        /* 生成 Message 对象。
         * "7127" 为目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
         * Conversation.ConversationType.PRIVATE 为私聊会话类型，根据需要，也可以传入其它会话类型，如群组，讨论组等。
         */
        Message myMessage = Message.obtain(msg.getUserId(), Conversation.ConversationType.PRIVATE, msg);
        /**
         * <p>发送消息。
         * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}
         * 中的方法回调发送的消息状态及消息体。</p>
         *
         * @param message     将要发送的消息体。
         * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
         *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
         *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
         * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
         * @param callback    发送消息的回调，参考 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}。
         */
        RongIM.getInstance().sendMessage(myMessage, msg.getContent(), null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                //消息本地数据库存储成功的回调
                SingleTipsDialog dialog = new SingleTipsDialog(activity, "温馨提示");
                dialog.setMessage("发送成功");
                dialog.setLeftBtn("确定", new Function1<View, Unit>() {
                    @Override
                    public Unit invoke(View view) {
                        return null;
                    }
                });
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        activity.finish();
                    }
                });
                dialog.showDialog();
            }

            @Override
            public void onSuccess(Message message) {
                //消息通过网络发送成功的回调

            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                //消息发送失败的回调
            }
        });
    }
    public static void sendMessage(ShareMessage msg, Conversation.ConversationType type, final Activity activity) {
        /* 生成 Message 对象。
         * "7127" 为目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
         * Conversation.ConversationType.PRIVATE 为私聊会话类型，根据需要，也可以传入其它会话类型，如群组，讨论组等。
         */
        Message myMessage = Message.obtain(msg.getUserId(), type, msg);
        /**
         * <p>发送消息。
         * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}
         * 中的方法回调发送的消息状态及消息体。</p>
         *
         * @param message     将要发送的消息体。
         * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
         *                    如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
         *                    如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
         * @param pushData    push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessage#getPushData()} 方法获取。
         * @param callback    发送消息的回调，参考 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}。
         */
        RongIM.getInstance().sendMessage(myMessage, msg.getContent(), null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                //消息本地数据库存储成功的回调
                SingleTipsDialog dialog = new SingleTipsDialog(activity, "温馨提示");
                dialog.setMessage("发送成功");
                dialog.setLeftBtn("确定", new Function1<View, Unit>() {
                    @Override
                    public Unit invoke(View view) {
                        return null;
                    }
                });
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        activity.finish();
                    }
                });
                dialog.showDialog();
            }

            @Override
            public void onSuccess(Message message) {
                //消息通过网络发送成功的回调

            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                //消息发送失败的回调
            }
        });
    }


    /**
     * 发送图片
     */
    public static void  sendImageMessage(ImageMessage message, Conversation.ConversationType type, String mTargetId, final Activity activity) {
        RongIM.getInstance().sendImageMessage(type, mTargetId, message, null, null, new RongIMClient.SendMediaMessageCallback() {
            @Override
            public void onAttached(Message message) {
                SingleTipsDialog dialog = new SingleTipsDialog(activity, "温馨提示");
                dialog.setMessage("发送成功");
                dialog.setLeftBtn("确定", new Function1<View, Unit>() {
                    @Override
                    public Unit invoke(View view) {
                        return null;
                    }
                });
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        activity.finish();
                    }
                });
                dialog.showDialog();
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {

            }

            @Override
            public void onSuccess(Message message) {

            }

            @Override
            public void onProgress(Message message, int i) {

            }
        });
    }

    /**
     * 修改名字
     * @param id
     * @param name
     * @param url
     */
    public static void changName(String id,String name,String url){
        io.rong.imlib.model.UserInfo userInfo = new io.rong.imlib.model.UserInfo(id,name, Uri.parse(url));
        RongIM.getInstance().refreshUserInfoCache(userInfo);
    }

    /**
     * 解决在页面底部置输入框，输入法弹出遮挡部分输入框的问题
     * @param root 页面根元素
     */
    public static void controlKeyboardLayout(final View root,
                                             final View editLayout) {
        // TODO Auto-generated method stub
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                Rect rect=new Rect();
                //获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                //获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInVisibleHeigh=root.getRootView().getHeight()-rect.bottom;
                //若不可视区域高度大于100，则键盘显示
                if (rootInVisibleHeigh > 100) {
                    int[] location = new int[2];
                    //获取editLayout在窗体的坐标
                    editLayout.getLocationInWindow(location);
                    //计算root滚动高度，使editLayout在可见区域
                    int srollHeight = (location[1] + editLayout.getHeight()) - rect.bottom;
                    root.scrollTo(0, srollHeight);
                } else {
                    //键盘隐藏
                    root.scrollTo(0, 0);
                }
            }
        });
    }


    /**
     * 计算时间差
     *
     * @param starTime
     *            开始时间
     * @param endTime
     *            结束时间
     *            返回类型 ==1----天，时，分。 ==2----时
     * @return 返回时间差
     */
    public static long getTimeDifference(String starTime, String endTime) {
        long timeString = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();

            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");
            long hour1 = diff / (60 * 60 * 1000);
            String hourString = hour1 + "";
            long min1 = ((diff / (60 * 1000)) - hour1 * 60);
            timeString = hour1;
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timeString;

    }

    /**
     * 保存图片到指定路径
     * Save image with specified size
     *
     * @param filePath the image file save path 储存路径
     * @param bitmap   the image what be save   目标图片
     * @param size     the file size of image   期望大小
     */
    public static File saveImage(String filePath, Bitmap bitmap, long size) {

        File result = new File(filePath.substring(0, filePath.lastIndexOf("/")));

        if (!result.exists() && !result.mkdirs()) return null;

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int options = 100;
        bitmap.compress(Bitmap.CompressFormat.JPEG, options, stream);

        while (stream.toByteArray().length / 1024 > size && options > 4) {
            stream.reset();
            options -= 4;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, stream);
        }

        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(stream.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new File(filePath);
    }
}
