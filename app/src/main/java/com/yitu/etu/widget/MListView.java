package com.yitu.etu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/21.
 */
public class MListView extends ListView{
    public MListView(Context context) {
        super(context);
    }

    public MListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
