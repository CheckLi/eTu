package com.yitu.etu.util;

import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.yitu.etu.EtuApplication;

/**
 * @className:ToastUtil
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月08日 16:30
 */
public class ToastUtil {
    /**
     * 默认提示框
     */
    private static Toast mToast;

    /**
     * 显示一个提示消息，并且关闭上一个提示，防止持续不断的显示toast
     *
     * @param strMsg
     */
    public static void showMessage(String strMsg) {
        if (mToast != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                mToast.cancel();
            }
        } else {
            mToast = ToastUtil.getToast();
        }
        if (TextUtils.isEmpty(strMsg)) {
            return;
        }
        mToast.setText(strMsg);
        mToast.show();
    }

    /**
     * 获取toast
     */
    public static Toast getToast() {
        Toast toast = Toast.makeText(EtuApplication.getInstance(),"",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        return toast;
    }
}
