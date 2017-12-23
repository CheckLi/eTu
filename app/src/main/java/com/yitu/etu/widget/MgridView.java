package com.yitu.etu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/21.
 */
public class MgridView extends GridView{
    public MgridView(Context context) {
        super(context);
    }

    public MgridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MgridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
