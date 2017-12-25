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
public class NoScrollAndNoAnimViewPage extends ViewPager{
    private boolean enabled = false;
    public NoScrollAndNoAnimViewPage(Context context) {
        super(context);
    }

    public NoScrollAndNoAnimViewPage(Context context, AttributeSet attrs) {
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
    //去除页面切换时的滑动翻页效果
    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        // TODO Auto-generated method stub
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        // TODO Auto-generated method stub
        super.setCurrentItem(item, false);
    }
}
