package com.yitu.etu.widget

import android.app.Activity
import android.content.Context
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.yitu.etu.R
import kotlinx.android.synthetic.main.actionbar_layout.view.*

/**
 *
 *@className:ActionBarView
 *@description:
 * @author: JIAMING.LI
 * @date:2017年12月09日 13:18
 *
 */
class ActionBarView : FrameLayout {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.actionbar_layout, this, true)
        setLeftImage{
            (context as Activity).onBackPressed()
        }
    }

    fun setTitle(title: String) {
        tv_title.text = title
    }

    fun setLeftImage(@DrawableRes drawableId: Int, onClick: OnClickListener) {
        iv_left.visibility=View.VISIBLE
        iv_left.setImageResource(drawableId)
        iv_left.setOnClickListener(onClick)
    }

    fun setLeftImage(onClick: OnClickListener) {
        iv_left.visibility=View.VISIBLE
        iv_left.setOnClickListener(onClick)
    }

    fun setRightImage(@DrawableRes drawableId: Int, onClick: OnClickListener) {
        iv_right.visibility=View.VISIBLE
        iv_right.setImageResource(drawableId)
        iv_right.setOnClickListener(onClick)
    }

    fun setRightImage(onClick: OnClickListener) {
        iv_right.visibility=View.VISIBLE
        iv_right.setOnClickListener(onClick)
    }

    fun setLeftImage(onClick: () -> Unit) {
        iv_left.visibility=View.VISIBLE
        iv_left.setOnClickListener {
            onClick()
        }
    }

    fun setLeftImage(@DrawableRes drawableId: Int, onClick: () -> Unit) {
        iv_left.visibility=View.VISIBLE
        iv_left.setImageResource(drawableId)
        iv_left.setOnClickListener {
            onClick()
        }
    }

    fun setRightImage(@DrawableRes drawableId: Int, onClick: () -> Unit) {
        iv_right.visibility=View.VISIBLE
        iv_right.setImageResource(drawableId)
        iv_right.setOnClickListener{
            onClick()
        }
    }

    fun setRightImage(onClick: () -> Unit) {
        iv_right.visibility=View.VISIBLE
        iv_right.setOnClickListener{
            onClick()
        }
    }

    fun hideLeftImage(){
        iv_left.visibility=View.GONE
    }

    fun hideRightImage(){
        iv_right.visibility=View.GONE
    }
}