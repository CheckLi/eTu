package com.yitu.etu.widget

import android.content.Context
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.yitu.etu.R
import kotlinx.android.synthetic.main.data_empty_layout.view.*

/**
 * @className:DataEmptyView
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月22日 17:58
 */
class DataEmptyView : FrameLayout {
    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    fun init(attrs: AttributeSet?) {
        LayoutInflater.from(context).inflate(R.layout.data_empty_layout, this, true)
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.DataEmptyView)
            val message = a.getString(R.styleable.DataEmptyView_message)
            val icon = a.getResourceId(R.styleable.DataEmptyView_icon, R.drawable.cart_default_bg)
            setMessage(message)
            setIcon(icon)
        }
        setOnClickListener {

        }
    }

    fun setMessage(message: String) {
        tv_message.text = message
    }

    fun setIcon(@DrawableRes res: Int) {
        icon.setImageResource(res)
    }
}
