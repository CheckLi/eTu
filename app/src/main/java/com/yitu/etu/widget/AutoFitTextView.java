package com.yitu.etu.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * 自定义TextView，文本内容自动调整字体大小以适应TextView的大小
 *
 * @author yzp
 */
public class AutoFitTextView extends TextView {
    public AutoFitTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AutoFitTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public AutoFitTextView(Context context) {
        super(context);
    }

    public AutoFitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Re size the font so the specified text fits in the text box assuming the
     * text box is the specified width.
     *
     * @param text
     */
    private void refitText(String text, int textViewWidth) {
        if (text == null || textViewWidth <= 0)
            return;
        Paint textPaint = new Paint();
        textPaint.set(this.getPaint());
        int availableTextViewWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        Rect boundsRect = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), boundsRect);
        float textWidth = textPaint.measureText(text);
        float textSize = getTextSize();
        while (textWidth > availableTextViewWidth) {
            textSize -= getResources().getDisplayMetrics().density*1;
            textPaint.setTextSize(textSize);
            textWidth = textPaint.measureText(text);
        }
        /**
         * 最小字体限制8
         */
        float size=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,8,getResources().getDisplayMetrics());
        if(textSize <0){
            textSize =16;
        }else if(textSize<size){
            textSize=size;
        }
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        refitText(this.getText().toString(), this.getWidth());
    }
}