package com.yitu.etu.ui.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.yitu.etu.dialog.LoadingDialog;
import com.yitu.etu.util.ToastUtil;

/**
 * @className:BaseActivity
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月08日 16:19
 */
public abstract class BaseActivity extends AppCompatActivity {
    private LoadingDialog mWaitDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        init();
        initActionBar();
        initView();
        getData();
        initListener();
    }

    private void init() {
        mWaitDialog = new LoadingDialog(this, "");
    }

    public abstract @LayoutRes
    int getLayout();

    public abstract void initActionBar();

    public abstract void initView();

    public abstract void getData();

    public abstract void initListener();


    /**
     * 隐藏系统软键盘
     */
    public void hideSoftInput() {
        if (getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @param msg
     */
    public void showToast(String msg) {
        ToastUtil.showMessage(msg);
    }

    /**
     * 显示等待框
     *
     * @param message
     */
    public void showWaitDialog(String message) {
        if (mWaitDialog == null) {
            mWaitDialog = new LoadingDialog(this, message);
        } else if (mWaitDialog.isShowing()) {
            mWaitDialog.dismiss();
        }
        mWaitDialog.setMessage(message);
        mWaitDialog.showDialog();
    }

    /**
     * 隐藏等待框
     */
    public void hideWaitDialog() {
        if (mWaitDialog != null) {
            mWaitDialog.hideDialog();
        }
    }
}
