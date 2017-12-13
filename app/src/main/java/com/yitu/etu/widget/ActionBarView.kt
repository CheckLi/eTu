package com.yitu.etu.widget

import android.app.Activity
import android.content.Context
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
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
        setLeftImage {
            (context as Activity).onBackPressed()
        }
    }

    fun setTitle(title: String) {
        tv_title.text = title
    }

    fun setLeftImage(@DrawableRes drawableId: Int, onClick: OnClickListener) {
        checkLeftVisibile(iv_left)
        iv_left.setImageResource(drawableId)
        iv_left.setOnClickListener(onClick)
    }

    fun setLeftImage(onClick: OnClickListener) {
        checkLeftVisibile(iv_left)
        iv_left.setOnClickListener(onClick)
    }

    fun setRightImage(@DrawableRes drawableId: Int, onClick: OnClickListener) {
        checkRightVisibile(iv_right)
        iv_right.setImageResource(drawableId)
        iv_right.setOnClickListener(onClick)
    }

    fun setRightImage(onClick: OnClickListener) {
        checkRightVisibile(iv_right)
        iv_right.setOnClickListener(onClick)
    }

    fun setLeftImage(onClick: () -> Unit) {
        checkLeftVisibile(iv_left)
        iv_left.setOnClickListener {
            onClick()
        }
    }

    fun setLeftImage(@DrawableRes drawableId: Int, onClick: () -> Unit) {
        checkLeftVisibile(iv_left)
        iv_left.setImageResource(drawableId)
        iv_left.setOnClickListener {
            onClick()
        }
    }

    fun setRightImage(@DrawableRes drawableId: Int, onClick: () -> Unit) {
        checkRightVisibile(iv_right)
        iv_right.setImageResource(drawableId)
        iv_right.setOnClickListener {
            onClick()
        }
    }

    fun setRightImage(onClick: () -> Unit) {
        checkRightVisibile(iv_right)
        iv_right.setOnClickListener {
            onClick()
        }
    }

    fun setLeftText(text:String,onClick: () -> Unit){
        checkLeftVisibile(tv_left_text)
        tv_left_text.text=text
        tv_left_text.setOnClickListener {
            onClick()
        }
    }

    fun setRightText(text:String,onClick: () -> Unit){
        checkLeftVisibile(tv_right_text)
        tv_right_text.text=text
        tv_right_text.setOnClickListener {
            onClick()
        }
    }

    fun setLeftText(text:String,onClick: OnClickListener){
        checkLeftVisibile(tv_left_text)
        tv_left_text.text=text
        tv_left_text.setOnClickListener(onClick)
    }

    fun setRightText(text:String,onClick: OnClickListener){
        checkRightVisibile(tv_right_text)
        tv_right_text.text=text
        tv_right_text.setOnClickListener(onClick)
    }

    fun hideLeftImage() {
        iv_left.visibility = View.GONE
    }

    fun hideRightImage() {
        iv_right.visibility = View.GONE
    }

    fun checkLeftVisibile(view: Any) {
        when (view) {
            is TextView -> {
                hideLeftImage()
                tv_left_text.visibility = View.VISIBLE
            }
            is ImageView -> {
                tv_left_text.visibility = View.GONE
                iv_left.visibility = View.VISIBLE
            }
        }
    }

    fun checkRightVisibile(view: Any) {
        when (view) {
            is TextView -> {
                hideRightImage()
                tv_right_text.visibility = View.VISIBLE
            }
            is ImageView -> {
                tv_right_text.visibility = View.GONE
                iv_right.visibility = View.VISIBLE
            }
        }
    }
}