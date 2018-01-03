package com.example.nanchen.calendarviewdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2018/1/2.
 */
public class MGridView extends GridView{
    public MGridView(Context context) {
        super(context);
    }

    public MGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
