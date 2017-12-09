package com.yitu.etu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.yitu.etu.dialog.LoadingDialog;
import com.yitu.etu.util.ToastUtil;

/**
 * @className:BaseFragment
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月08日 17:36
 */
public abstract class BaseFragment extends Fragment {
    private LoadingDialog mWaitDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initView();
        getData();
        initListener();

    }

    private void init() {
        mWaitDialog = new LoadingDialog(getActivity(), "");
    }

    public abstract @LayoutRes
    int getLayout();

    public abstract void initView();

    public abstract void getData();

    public abstract void initListener();

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int id) {
        try {
            return (T) getView().findViewById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 隐藏系统软键盘
     */
    public void hideSoftInput() {
        if (getActivity().getCurrentFocus() != null) {
            if (getActivity().getCurrentFocus().getWindowToken() != null) {
                ((InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
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
            mWaitDialog = new LoadingDialog(getActivity(), message);
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
