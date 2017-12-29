package com.yitu.etu.widget.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.yitu.etu.R;

/**
 * 1.BitmapShader
 *
 * @author Long
 */
public class RoundImageView2 extends android.support.v7.widget.AppCompatImageView {
    /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
    private float[] rids = {0f, 0f, 0f, 0f, 0.0f, 0.0f, 0.0f, 0.0f,};

    private final int DEFAULT_RADIUS = 0;
    private int topLeftRadius;
    private int topRightRadius;

    public RoundImageView2(Context context) {
        super(context);
    }

    public RoundImageView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageViewT, defStyleAttr, 0);
        topLeftRadius = a.getDimensionPixelSize(R.styleable.RoundImageViewT_topLeftRadius, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_RADIUS, getResources().getDisplayMetrics()));
        topRightRadius = a.getDimensionPixelSize(R.styleable.RoundImageViewT_topRightRadius, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_RADIUS, getResources().getDisplayMetrics()));
        rids[0] = topLeftRadius;
        rids[1] = topLeftRadius;
        rids[2] = topRightRadius;
        rids[3] = topRightRadius;
        a.recycle();
    }

    public void setTopRadius(int topRadius){
        rids[0] = topRadius;
        rids[1] = topRadius;
        rids[2] = topRadius;
        rids[3] = topRadius;
    }
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, widthMeasureSpec * 2 / 3);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG));
        Path path = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。radii长度必须为8*/
        path.addRoundRect(new RectF(0, 0, w, h), rids, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);

    }
}
