package com.yitu.etu.Receiver;

import android.content.Context;
import android.content.Intent;

import com.yitu.etu.ui.activity.MainActivity;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * @className:SealNotificationReceiver
 * @description:处理消息接收后的消息处理
 * @author: JIAMING.LI
 * @date:2017年12月12日 10:45
 */
public class SealNotificationReceiver extends PushMessageReceiver {
    /* push 通知到达事件*/
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage message) {
        return false; // 返回 false, 会弹出融云 SDK 默认通知; 返回 true, 融云 SDK 不会弹通知, 通知需要由您自定义。
    }

    /* push 通知点击事件 */
    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage message) {
        Intent intent=new Intent(context,MainActivity.class);
        intent.putExtra("defaultPosition",0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        return true; // 返回 false, 会走融云 SDK 默认处理逻辑, 即点击该通知会打开会话列表或会话界面; 返回 true, 则由您自定义处理逻辑。
    }
}