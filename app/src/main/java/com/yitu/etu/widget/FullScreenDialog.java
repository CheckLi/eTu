package com.yitu.etu.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2018/1/5.
 */
public class FullScreenDialog extends Dialog {
    private boolean cancel = true;
    LinearLayout linearLayout;
    boolean isFullScreen = false;

    public FullScreenDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public FullScreenDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    protected FullScreenDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }


    public void init() {
        linearLayout = new LinearLayout(getContext());
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        fullScreen(false);
    }

    public void setGravity(int gravity) {
        linearLayout.setGravity(gravity);
        fullScreen(isFullScreen);
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        this.cancel = cancel;
    }

    //设置全屏显示
    public void fullScreen(boolean isFullScreen) {
        this.isFullScreen = isFullScreen;
        if (isFullScreen) {
            linearLayout.setPadding(0, 0, 0, 0);
        } else {
            int padding=dp2px(getContext(), 20);
            linearLayout.setPadding(padding, padding, padding, padding);
        }
    }

    public int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public void setContentView(@NonNull View view) {
        view.setFocusable(true);
        view.setClickable(true);
        linearLayout.addView(view);
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setGravity(Gravity.CENTER);
        super.setContentView(linearLayout);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.FILL_PARENT;
        window.setAttributes(lp);
        setCanceledOnTouchOutside(true);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancel) {
                    dismiss();
                }
            }
        });
    }
}
