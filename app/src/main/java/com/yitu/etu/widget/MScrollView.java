package com.yitu.etu.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @className:MScrollView
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月26日 21:42
 */
public class MScrollView extends ScrollView{
    public MScrollView(Context context) {
        super(context);
    }

    public MScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
 boolean first=true;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(first){
            first=false;
            scrollTo(0,0);
        }
    }
}
