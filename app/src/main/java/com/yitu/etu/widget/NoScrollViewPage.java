package com.yitu.etu.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * @className:NoScrollViewPage
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月09日 18:52
 */
public class NoScrollViewPage extends ViewPager{
    private boolean enabled = false;
    public NoScrollViewPage(Context context) {
        super(context);
    }

    public NoScrollViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean executeKeyEvent(KeyEvent event) {
        return false;
    }

    @Override
    public boolean arrowScroll(int direction) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
