package com.yitu.etu.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import static android.support.v4.widget.ViewDragHelper.Callback;
import static android.support.v4.widget.ViewDragHelper.create;

/**
 * @className:SuspensionDragButton
 * @description:用于添加悬浮窗效果，靠边吸顶，默认取最后一个view作为可拖动控件
 * @author: JIAMING.LI
 * @date:2017年12月05日 10:23
 */
public class SuspensionDragButton extends RelativeLayout {
    private ViewDragHelper mDragHelper;
    private View mDragView;
    private boolean isInit;
    private onDragButtonClickListener mClickListener;

    public SuspensionDragButton(@NonNull Context context) {
        super(context);
        init();
    }

    public SuspensionDragButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SuspensionDragButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SuspensionDragButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mDragHelper = create(this, 1.0f, new DragHelperCallback());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!isInit) {
            super.onLayout(changed, l, t, r, b);
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
    }

    @Override
    public void postInvalidate() {
        super.postInvalidate();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    boolean isDrag = true;
    float x = 0, y = 0;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mDragHelper.processTouchEvent(ev);

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            x = ev.getX();
            y = ev.getY();
            isDrag = false;
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            float offsetX=Math.abs(x - ev.getX());
            float offsetY=Math.abs(y - ev.getY());
            if ( offsetX> 50 || offsetY > 50) {
                isDrag = true;
            }
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            boolean b = x > mDragView.getLeft() && x < mDragView.getRight() && y > mDragView.getTop() && y < mDragView.getBottom() && mDragView.getVisibility() == VISIBLE;
            if (!isDrag && mClickListener != null && b) {
                mClickListener.onClick(mDragView);
            }
            x=0;
            y=0;
        }
        boolean b = x > mDragView.getLeft() && x < mDragView.getRight() && y > mDragView.getTop() && y < mDragView.getBottom() && mDragView.getVisibility() == VISIBLE;
        return b;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 0) {
            throw new NullPointerException("至少添加一个控件");
        }
        //默认取最后一个view可以滑动
        mDragView = getChildAt(getChildCount() - 1);
    }


    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    /**
     * 隐藏悬浮按钮
     */
    public void hideDragButton() {
        if (mDragView != null) {
            mDragView.setVisibility(GONE);
        }
    }

    /**
     * 显示悬浮按钮
     */
    public void showDragButton() {
        if (mDragView != null) {
            mDragView.setVisibility(VISIBLE);
        }
    }

    public View getDragView() {
        return mDragView;
    }

    public void setDragViewListener(onDragButtonClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }


    class DragHelperCallback extends Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mDragView;
        }

        //横向滑动的时候调用
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            final int leftBound = getPaddingLeft();
            final int rightBound = getWidth() - mDragView.getWidth();
            final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
            return newLeft;
        }

        //纵向滑动的时候调用
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - mDragView.getHeight();
            final int newTop = Math.min(Math.max(top, topBound), bottomBound);
            return newTop;
        }

        //手指释放的时候回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int widthMiddle = getWidth() >> 1;
            int childWidthMiddle = releasedChild.getLeft() + (releasedChild.getWidth() >> 1);
            int offsetLeft = widthMiddle - childWidthMiddle;
            if (offsetLeft > 0) {
                mDragHelper.settleCapturedViewAt(0, mDragView.getTop());
            } else {
                mDragHelper.settleCapturedViewAt(getWidth() - mDragView.getWidth(), mDragView.getTop());
            }
            isInit = true;
            invalidate();
        }
    }

    public interface onDragButtonClickListener {
        void onClick(View view);
    }
}
