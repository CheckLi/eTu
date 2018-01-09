package com.yitu.etu.util

import android.app.Application
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import android.view.inputmethod.EditorInfo
import com.yitu.etu.EtuApplication
import com.yitu.etu.dialog.InputPriceDialog
import com.yitu.etu.entity.ObjectBaseEntity
import com.yitu.etu.entity.UserInfo
import com.yitu.etu.eventBusItem.EventRefreshName
import com.yitu.etu.tools.GsonCallback
import com.yitu.etu.tools.Http
import com.yitu.etu.tools.Urls
import com.yitu.etu.ui.activity.BaseActivity
import okhttp3.Call
import org.greenrobot.eventbus.EventBus
import java.lang.Exception
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

/**
 *
 *@className:KotlinUtil
 *@description:
 * @author: JIAMING.LI
 * @date:2017年12月07日 17:05
 *
 */

/**
 * 检查字符串是否为空，空则返回默认值
 */
fun String?.Empty(default: String): String {
    return if (this.isNullOrBlank()) {
        default
    } else {
        this!!
    }
}

fun String?.Empty(): String {
    return this.Empty("")
}

/**
 * 检查list是不是空
 */
fun List<Any>?.checkEmpty(): Boolean {
    if (this == null || this.isEmpty() || this.size == 0) {
        return false
    }

    return true
}

/**
 * 判断view是不是空，是空则返回隐藏或者显示
 */
fun String?.checkVisible(vararg hide: String): Int {
    if (this.isNullOrBlank() || hide.contains(this)) {
        return View.GONE
    }
    return View.VISIBLE
}

/**
 * 根据字符串判断是否显示隐藏，null或者空格则会隐藏
 */
fun View?.checkVisible(content: String) {
    this?.visibility = content.checkVisible()
}

fun Context.showToast(message: String) {
    ToastUtil.showMessage(message)
}

fun Application.showToast(message: String) {
    ToastUtil.showMessage(message)
}

/**
 * 获取用户信息
 */
fun Context.userInfo(): UserInfo {
    return EtuApplication.getInstance().userInfo
}

/**
 * 获取用户信息
 */
fun Fragment.userInfo(): UserInfo {
    return EtuApplication.getInstance().userInfo
}

/**
 * 获取时间
 */
fun Long.getTime(format: String? = null): String {
    return DateUtil.getTime(this.toString(), format)
}

/**
 * 格式化金额
 */
fun Float.formatPrice(): String {
    val mDfScore = DecimalFormat("#.00")
    mDfScore.roundingMode = RoundingMode.DOWN
    return mDfScore.format(this)
}

/**
 * 修改备注名
 */
fun BaseActivity.changOtherName(id: String) {
    var dialog = InputPriceDialog(this, "修改备注")
    dialog.setHint("请输入备注名", false, EditorInfo.TYPE_CLASS_TEXT)
    /**
     * 修改备注名
     */
    dialog.setRightBtnResultText("确认", "请输入备注名") {
        showWaitDialog("修改中...")
        post(Urls.URL_CHANG_BEIZHU, hashMapOf("name" to it, "user_id" to id.Empty()), object : GsonCallback<ObjectBaseEntity<Any>>() {
            override fun onError(call: Call?, e: Exception?, id: Int) {
                showToast("修改失败")
                hideWaitDialog()
            }

            override fun onResponse(response: ObjectBaseEntity<Any>, id1: Int) {
                hideWaitDialog()
                if (response.success() && response.data != null) {
                    val map = HashMap<String, String>()
                    map.put("user_id", id)
                    Http.post(Urls.URL_GET_ID_USER_INFO, map, object : GsonCallback<ObjectBaseEntity<UserInfo>>() {
                        override fun onError(call: Call, e: Exception, id: Int) {
                            LogUtil.e(e.localizedMessage)

                        }

                        override fun onResponse(response: ObjectBaseEntity<UserInfo>, id: Int) {
                            if (response.success()) {
                                val (id2, name, _, _, _, _, _, _, _, header) = response.data
                                var url = Urls.address + header
                                if (url.startsWith(Urls.address + "http")) {
                                    url = url.replace(Urls.address, "")
                                }
                                Tools.changName(id2.toString(), name, url)
                            }
                        }
                    })
                    response.data?.run {
                        showToast("修改成功")
                        EventBus.getDefault().post(EventRefreshName(it))
                        dialog.dismiss()
                    }
                } else {
                    showToast(response.message)
                }
            }

        })

    }
    dialog.showDialog()
}