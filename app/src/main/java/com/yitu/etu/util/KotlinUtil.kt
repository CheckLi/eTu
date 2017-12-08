package com.yitu.etu.util

import android.view.View

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
