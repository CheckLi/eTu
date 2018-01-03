package com.yitu.etu.ui.fragment.Chat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.Gravity
import com.amap.api.maps.model.LatLng
import com.huizhuang.zxsq.utils.nextActivityFromFragment
import com.yitu.etu.EtuApplication
import com.yitu.etu.R
import com.yitu.etu.dialog.InputPriceDialog
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.PinAnBean
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Http
import com.yitu.etu.tools.Urls
import com.yitu.etu.ui.activity.BaseActivity
import com.yitu.etu.ui.activity.MapActivity
import com.yitu.etu.util.userInfo
import com.yitu.etu.widget.chat.PacketMessage
import io.rong.imkit.DefaultExtensionModule
import io.rong.imkit.RongExtension
import io.rong.imkit.RongIM
import io.rong.imkit.plugin.IPluginModule
import io.rong.imlib.IRongCallback
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import io.rong.imlib.model.Message
import io.rong.message.ImageMessage
import io.rong.message.LocationMessage
import okhttp3.Call
import java.io.File
import java.lang.Exception


/**
 *
 *@className:ChatFragment
 *@description:
 * @author: JIAMING.LI
 * @date:2017年12月31日 09:50
 *
 */
var mTargetId = ""

/**
 * 自定义面板，0拍摄，1位置，2位置共享，3平安符
 */
class MyPlugin(val type: Int) : IPluginModule {
    override fun onClick(p0: Fragment, p1: RongExtension?) {
        val activity: Activity = p0.activity
        mTargetId = activity.intent.data.getQueryParameter("targetId")
        if (activity is BaseActivity) {
            when (type) {
                0 -> {
                    activity.Camera(true, 3, 4, 720, 1280)
                    activity.setSuccessListener {
                        sendImageMessage(it)
                    }
                }
                3 -> {
                    val dialog = InputPriceDialog(p0.activity, "发送平安符")
                    val xy = dialog.setHint("输入平安符数量", true, Gravity.CENTER_HORIZONTAL, InputType.TYPE_CLASS_PHONE)
                    xy.text = "当前余量：${activity.userInfo()?.safecount}"
                    dialog.setRightBtnResultText("确认", "请输入平安符数量") {
                        sendPan("1", it, activity)
                    }
                    dialog.showDialog()
                }
                1->p0.nextActivityFromFragment<MapActivity>(1001)
                else -> {
                    val mes = PacketMessage.obtain("李佳明的平安符","1","1","22")
                    val message = Message.obtain(mTargetId, Conversation.ConversationType.PRIVATE, mes)
                    sendMessage(message)
                }

            }
        }
    }

    /**
     * 发送平安符
     */
    fun sendPan(peopleCount: String, count: String, activity: BaseActivity) {
        activity.showWaitDialog("发送中...")
        Http.post(Urls.URL_SEND_PIN_A_FU, hashMapOf("count" to count, "people" to peopleCount), object : GsonCallback<ObjectBaseEntity<PinAnBean>>() {
            override fun onResponse(response: ObjectBaseEntity<PinAnBean>, id: Int) {
                activity.hideWaitDialog()
                if (response.success()) {
                    with(response.data) {
                        val mes = PacketMessage.obtain("平安符赠送",count,people,idPin)
                        val message = Message.obtain(mTargetId, Conversation.ConversationType.PRIVATE, mes)
                        sendMessage(message)
                    }
                } else {
                    activity.showToast(response.message)
                }
            }

            override fun onError(call: Call?, e: Exception?, id: Int) {
                activity.hideWaitDialog()
                activity.showToast("发送失败")
            }

        })
    }

    override fun obtainDrawable(p0: Context?): Drawable {
        return EtuApplication.getInstance().resources.getDrawable(when (type) {
            0 -> R.drawable.chat_location
            1 -> R.drawable.rc_ext_plugin_location
            2 -> R.drawable.rc_ext_plugin_location
            3 -> R.drawable.icon77
            else -> R.drawable.chat_location
        })
    }

    override fun obtainTitle(p0: Context?): String {
        return when (type) {
            0 -> "拍摄"
            1 -> "位置"
            2 -> "位置共享"
            3 -> "平安符"
            else -> "其他"
        }
    }

    override fun onActivityResult(p0: Int, p1: Int, data: Intent?) {

        when (type) {
            1 -> {
                if (data != null) {
                    val latLng = data?.getParcelableExtra<Parcelable>("latLng") as LatLng
                    val address = data.getStringExtra("address")
                    val uri = data?.getParcelableExtra<Uri>("image")
                    sendLocation(latLng.latitude, latLng.longitude, address, uri)
                }
            }
            else -> ""
        }
    }

}


/**
 * 自定义功能面板
 */
class MyExtensionModule : DefaultExtensionModule() {

    override fun getPluginModules(conversationType: Conversation.ConversationType): List<IPluginModule> {
        val pluginModules = super.getPluginModules(conversationType)
        if (pluginModules.size > 1) {
            pluginModules.removeAt(1)
        }
        pluginModules.add(MyPlugin(0))
        pluginModules.add(MyPlugin(1))
        pluginModules.add(MyPlugin(2))
        pluginModules.add(MyPlugin(3))

        return pluginModules
    }
}

/**
 * 发送图片
 */
fun sendImageMessage(path: String) {
    RongIM.getInstance().sendImageMessage(Conversation.ConversationType.PRIVATE, mTargetId, ImageMessage.obtain(Uri.fromFile(File(path)), Uri.fromFile(File(path)), false), null, null, null)
}

fun sendLocation(lat: Double, lng: Double, poi: String, thumb: Uri) {
    val locationMessage = LocationMessage.obtain(lat, lng, poi, thumb)
    val message = Message.obtain(mTargetId, Conversation.ConversationType.PRIVATE, locationMessage)
    sendMessage(message)
}

fun sendMessage(myMessage: Message) {
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
    RongIM.getInstance().sendMessage(myMessage, null, null, object : IRongCallback.ISendMessageCallback {
        override fun onAttached(message: Message) {
            //消息本地数据库存储成功的回调
        }

        override fun onSuccess(message: Message) {
            //消息通过网络发送成功的回调
        }

        override fun onError(message: Message, errorCode: RongIMClient.ErrorCode) {
            //消息发送失败的回调
        }
    })
}
fun sendMessage(myMessage: Message,pushContent:String) {
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
    RongIM.getInstance().sendMessage(myMessage, pushContent, null, object : IRongCallback.ISendMessageCallback {
        override fun onAttached(message: Message) {
            //消息本地数据库存储成功的回调
        }

        override fun onSuccess(message: Message) {
            //消息通过网络发送成功的回调
        }

        override fun onError(message: Message, errorCode: RongIMClient.ErrorCode) {
            //消息发送失败的回调
        }
    })
}