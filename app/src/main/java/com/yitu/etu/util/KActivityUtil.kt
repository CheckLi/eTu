package com.huizhuang.zxsq.utils

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.yitu.etu.util.activityUtil
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