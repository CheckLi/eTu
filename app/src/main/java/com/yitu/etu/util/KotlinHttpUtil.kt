package com.yitu.etu.util

import android.content.Context
import android.support.v4.app.Fragment
import com.yitu.etu.EtuApplication
import com.yitu.etu.tools.Http
import com.yitu.etu.tools.Urls
import com.zhy.http.okhttp.callback.Callback

/**
 *
 *@className:KotlinHttpUtil
 *@description:
 * @author: JIAMING.LI
 * @date:2017年12月19日 14:04
 *
 */

/**
 * 进入下一个activity
 */
inline fun <reified T : Any> Fragment.post(url: String, params: HashMap<String, String>, callback: Callback<T>) {
    Http.post(url, params, callback)
}

/**
 * 判断是否登陆
 */
fun Fragment.isLogin(): Boolean {
    return EtuApplication.getInstance().isLogin
}
/**
 * 判断是否登陆
 */
fun Context.isLogin(): Boolean {
    return EtuApplication.getInstance().isLogin
}

fun String.addHost():String{
    return Urls.address+this
}