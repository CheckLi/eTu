package com.huizhuang.zxsq.utils

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.yitu.etu.ui.activity.LoginActivity
import com.yitu.etu.util.activityUtil
import com.yitu.etu.util.isLogin
import org.jetbrains.anko.internals.AnkoInternals

/**
 *
 *@className:NextUtil
 *@description:
 * @author: JIAMING.LI
 * @date:2017年10月18日 17:32
 *
 */
/**
 * 进入下一个activity
 */
inline fun <reified T : Activity> Context.nextActivity(vararg params: Pair<String, Any?>) {
    val bundle = AnkoInternals.createIntent(this, T::class.java, params).extras
    activityUtil.nextActivity(this, T::class.java, bundle, false)
}

/**
 * 进入下一个activity
 */
inline fun <reified T : Activity> Fragment.nextActivityFromFragment(vararg params: Pair<String, Any?>) {
    val bundle = AnkoInternals.createIntent(this.activity, T::class.java, params).extras
    activityUtil.nextActivity(this, T::class.java, bundle, -1, false)
}

/**
 * 进入下一个activity
 */
inline fun <reified T : Activity> Context.nextActivity(request: Int, vararg params: Pair<String, Any?>) {
    val bundle = AnkoInternals.createIntent(this, T::class.java, params).extras
    activityUtil.nextActivity(this as Activity?, T::class.java, bundle, request, false)
}


/**
 * 进入下一个activity
 */
inline fun <reified T : Activity> Fragment.nextActivityFromFragment(request: Int, vararg params: Pair<String, Any?>) {
    val bundle = AnkoInternals.createIntent(this.activity, T::class.java, params).extras
    activityUtil.nextActivity(this, T::class.java, bundle, request, false)
}


/**
 * 进入下一个activity
 */
inline fun <reified T : Activity> Fragment.nextActivityFromFragment(finish: Boolean) {
    activityUtil.nextActivity(this, T::class.java, null, -1, finish)
}

/**
 * 进入下一个activity
 */
inline fun <reified T : Activity> Context.nextActivity(finish: Boolean) {
    activityUtil.nextActivity(this, T::class.java, null, finish)
}

/**
 * 进入下一个activity
 */
inline fun <reified T : Activity> Context.nextActivity(requestCode: Int) {
    activityUtil.nextActivity(this, T::class.java, null,requestCode, false)
}

/**
 * 进入下一个activity,需要检查是否登陆，如果未登录需要跳转到登陆界面
 */
inline fun <reified T : Activity> Context.nextCheckLoginActivity() {
    if(isLogin()) {
        activityUtil.nextActivity(this, T::class.java, null, false)
    }else{
        activityUtil.nextActivity(this, LoginActivity::class.java, null, false)
    }
}

/**
 * 进入下一个activity,需要检查是否登陆，如果未登录需要跳转到登陆界面
 */
inline fun <reified T : Activity> Fragment.nextCheckLoginActivity() {
    if(isLogin()) {
        activityUtil.nextActivity(this, T::class.java, null, false)
    }else{
        activityUtil.nextActivity(this, LoginActivity::class.java, null, false)
    }
}