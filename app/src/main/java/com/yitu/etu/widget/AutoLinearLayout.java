package com.yitu.etu.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.yitu.etu.R;


/**
 * @className:AutoLinearLayout
 * @description:
 * @author: JIAMING.LI
 * @date:2017年11月14日 14:22
 */
public class AutoLinearLayout extends LinearLayout {
    int numbers=0;
    public AutoLinearLayout(Context context) {
        super(context);
    }

    public AutoLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        attr(attrs);
    }

    public AutoLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        attr(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        attr(attrs);
    }

    private void attr(AttributeSet attrs){
        if(attrs!=null){
            TypedArray type=getContext().obtainStyledAttributes(attrs, R.styleable.AutoLinearLayout);
            numbers=type.getInt(R.styleable.AutoLinearLayout_numbers_index,0);
            type.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        if (count > 0) {
           try{
               if(numbers>count-1){
                   numbers=0;
               }
               // 获得它的父容器为它设置的测量模式和大小
               int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
               sizeWidth=sizeWidth-getPaddingRight()-getPaddingLeft();
               for (int i = count - 1; i >= 0; i--) {
                   View v = getChildAt(i);
                   //测量每一个子view的宽和高
                   measureChild(v, widthMeasureSpec, heightMeasureSpec);
                   //获取到测量的宽和高
                   int childWidth = v.getMeasuredWidth();
                   MarginLayoutParams mlp = (MarginLayoutParams) v.getLayoutParams();
                   if (i != numbers && v.getVisibility() == VISIBLE) {
                       sizeWidth =sizeWidth-childWidth-mlp.leftMargin-mlp.rightMargin;//需要将view的左右边距减去
                   }
               }
               View v = getChildAt(numbers);
               //获取到测量的宽和高
               MarginLayoutParams mlp = (MarginLayoutParams) v.getLayoutParams();
               int childWidth = v.getMeasuredWidth();
               if (childWidth >= sizeWidth) {
                   mlp.width = sizeWidth;
               }
               v.setLayoutParams(mlp);
           }catch(Exception e){
               e.printStackTrace();
           }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
