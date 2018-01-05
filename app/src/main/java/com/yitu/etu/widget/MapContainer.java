package com.yitu.etu.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2018/1/4.
 */
public class MapContainer extends LinearLayout {
    public ScrollView scrollView;
    public MapContainer(Context context) {
        super(context);
    }

    public MapContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MapContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(ev.getAction()==MotionEvent.ACTION_UP){
            scrollView.requestDisallowInterceptTouchEvent(false);
        }
        else{

            scrollView.requestDisallowInterceptTouchEvent(true);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
