package com.yitu.etu.widget

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView

/**
 * @className:TextViewDelLine
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月24日 12:53
 */
class TextViewDelLine : TextView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}
    init {
        paint.flags=Paint.STRIKE_THRU_TEXT_FLAG
    }
}
