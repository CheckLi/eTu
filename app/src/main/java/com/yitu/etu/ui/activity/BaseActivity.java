package com.yitu.etu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yitu.etu.Iinterface.ImageSelectSuccessListener;
import com.yitu.etu.R;
import com.yitu.etu.dialog.LoadingDialog;
import com.yitu.etu.tools.MyActivityManager;
import com.yitu.etu.util.ImageUtil;
import com.yitu.etu.util.ToastUtil;
import com.yitu.etu.widget.ActionBarView;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISCameraConfig;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @className:BaseActivity
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月08日 16:19
 */
public abstract class BaseActivity extends AppCompatActivity {
    public int page = 1;
    public int size = 10;
    public boolean add = true;
    public String className;
    private LoadingDialog mWaitDialog;
    public static final int REQUEST_LIST_CODE = 0;
    public static final int REQUEST_CAMERA_CODE = 1;
    public ActionBarView mActionBarView;
    public ImageSelectSuccessListener mSuccessListener;
    public Context context;
    public boolean isCrop = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        className = getClass().getSimpleName();
        setContentView(getLayout());
        getIntentExtra(getIntent());
        MyActivityManager.getInstance().addActivity(this);
        init();
        initActionBar();
        initView();
        getData();
        initListener();
    }


    public void setSuccessListener(ImageSelectSuccessListener successListener) {
        mSuccessListener = successListener;
    }

    private void init() {
        mWaitDialog = new LoadingDialog(this, "");
    }

    public void Multiselect() {
        ISListConfig config = new ISListConfig.Builder()
                .multiSelect(true)
                // 是否记住上次选中记录
                .rememberSelected(false)
                // 使用沉浸式状态栏
                .statusBarColor(getResources().getColor(R.color.actionBarColor))
                // 返回图标ResId
                .backResId(R.drawable.icon41)
                .titleBgColor(getResources().getColor(R.color.actionBarColor))
                .title("图片选择")
                .titleColor(Color.WHITE).build();

        ISNav.getInstance().toListActivity(this, config, REQUEST_LIST_CODE);
    }

    public void Single() {
        Single(true);
    }

    public void Single(boolean crop) {
        this.isCrop = crop;
        ISListConfig config = new ISListConfig.Builder()
                // 是否多选
                .multiSelect(false)
                // 确定按钮背景色
                //.btnBgColor(Color.parseColor(""))
                // 确定按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(getResources().getColor(R.color.actionBarColor))
                // 返回图标ResId
                .backResId(R.drawable.icon41)
                .title("图片选择")
                .titleColor(Color.WHITE)
                .titleBgColor(getResources().getColor(R.color.actionBarColor))
                .allImagesText("All Images")
                .needCrop(crop)
                .cropSize(1, 1, 200, 200)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(9)
                .build();

        ISNav.getInstance().toListActivity(this, config, REQUEST_LIST_CODE);
    }


    public void Single(boolean isShowCam,boolean isCrop, int aspectX, int aspectY, int outx, int outy) {
        this.isCrop = isCrop;
        ISListConfig config = new ISListConfig.Builder()
                // 是否多选
                .multiSelect(false)
                // 确定按钮背景色
                //.btnBgColor(Color.parseColor(""))
                // 确定按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(getResources().getColor(R.color.actionBarColor))
                // 返回图标ResId
                .backResId(R.drawable.icon41)
                .title("图片选择")
                .titleColor(Color.WHITE)
                .titleBgColor(getResources().getColor(R.color.actionBarColor))
                .allImagesText("All Images")
                .needCrop(isCrop)
                .cropSize(aspectX, aspectY, outx, outy)
                // 第一个是否显示相机
                .needCamera(isShowCam)
                // 最大选择图片数量
                .maxNum(9)
                .build();

        ISNav.getInstance().toListActivity(this, config, REQUEST_LIST_CODE);
    }

    public void Camera(boolean isCrop, int aspectX, int aspectY, int outx, int outy) {
        this.isCrop = isCrop;
        ISCameraConfig config = new ISCameraConfig.Builder()
                .needCrop(isCrop)
                .cropSize(aspectX, aspectY, outx, outy)
                .build();

        ISNav.getInstance().toCameraActivity(this, config, REQUEST_CAMERA_CODE);
    }

    public void Camera() {
        Camera(true, 1, 1, 200, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LIST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            zoomImage(pathList);
        } else if (requestCode == REQUEST_CAMERA_CODE && resultCode == RESULT_OK && data != null) {
            String path = data.getStringExtra("result");
            selectSuccess(path);
            imageResult(path);
        }
    }

    /**
     * 压缩图片
     */
    private void zoomImage(final List<String> pathList) {
        showWaitDialog("压缩中...");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        final String toPath = ImageUtil.cachePath + sdf.format(new Date()) + ".png";
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (pathList.size() > 0) {
                    try {
                        String path = pathList.get(0);
                        ImageUtil.transImage(path, toPath, 1, 400, 50);
                        Message msg = mHandler1.obtainMessage();
                        msg.obj = toPath;
                        msg.what = 1;
                        msg.sendToTarget();
                    } catch (Exception e) {
                        e.printStackTrace();
                        mHandler1.sendEmptyMessage(2);
                    }
                }
            }
        }).start();


    }

    /**
     * 处理图片压缩后值的返回
     */
    Handler mHandler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            hideWaitDialog();
            if (msg.what == 1) {
                String toPath = (String) msg.obj;
                List<String> list = new ArrayList<>();
                list.add(toPath);
                selectSuccess(list);
                if (list.size() > 0) {
                    imageResult(toPath);
                }
            }
            super.handleMessage(msg);
        }
    };

    public abstract @LayoutRes
    int getLayout();

    public abstract void initActionBar();

    public abstract void initView();

    public abstract void getData();

    public abstract void initListener();

    public void getIntentExtra(Intent intent) {

    }

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
     * 延时显示键盘
     *
     * @param v
     * @param delayTime
     */
    public void showSoftInput(final View v, long delayTime) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                m.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
            }
        }, delayTime);
    }

    /**
     * 弹出键盘
     */
    public void showSoftInput() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //强制显示或者关闭系统键盘
    public static void KeyBoard(final EditText txtSearchKey, final String status) {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager)
                        txtSearchKey.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (status.equals("open")) {
                    m.showSoftInput(txtSearchKey, InputMethodManager.SHOW_FORCED);
                } else {
                    m.hideSoftInputFromWindow(txtSearchKey.getWindowToken(), 0);
                }
            }
        }, 300);
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

    public void imageResult(String path) {
        if (mSuccessListener != null) {
            mSuccessListener.selectSuccess(path);
        }
    }

    public void selectSuccess(String path) {

    }

    public void selectSuccess(List<String> pathList) {

    }

    /**
     * 销毁activity的时候移除mangager的activity
     */
    @Override
    protected void onDestroy() {
        MyActivityManager.getInstance().removeActivity(this);
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.exit_open_animation, R.anim.exit_close_animation);

    }

    /**
     * 标题栏的添加
     *
     * @param layoutResID
     */
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        //添加标题栏
        FrameLayout layout = (FrameLayout) getWindow().getDecorView();
        mActionBarView = new ActionBarView(this);
        try {
            LinearLayout content = (LinearLayout) layout.getChildAt(0);
            content.addView(mActionBarView, 0);
        } catch (Exception e) {
            e.printStackTrace();
            layout.addView(mActionBarView);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mActionBarView.setTitle(title.toString());
        super.setTitle(title);
    }

    /**
     * 按钮点击事件
     *
     * @param listener
     */
    public void setLeftClick(View.OnClickListener listener) {
        mActionBarView.setLeftImage(listener);
    }

    /**
     * 按钮点击事件
     *
     * @param listener
     */
    public void setLeftClick(@DrawableRes int drawable, View.OnClickListener listener) {
        mActionBarView.setLeftImage(drawable, listener);
    }

    /**
     * 按钮点击事件
     *
     * @param listener
     */
    public void setRightClick(View.OnClickListener listener) {
        mActionBarView.setRightImage(listener);
    }

    /**
     * 按钮点击事件
     *
     * @param listener
     */
    public void setRightClick(@DrawableRes int drawable, View.OnClickListener listener) {
        mActionBarView.setRightImage(drawable, listener);
    }

    /**
     * 按钮点击事件
     *
     * @param listener
     */
    public void setRightClick(@DrawableRes int drawable, int color, View.OnClickListener listener) {
        mActionBarView.setRightImage(drawable, color, listener);
    }

    /**
     * 设置左侧文字按钮点击事件
     *
     * @param text
     * @param onClickListener
     */
    public void setLeftText(String text, View.OnClickListener onClickListener) {
        mActionBarView.setLeftText(text, onClickListener);
    }

    /**
     * \
     * 设置右侧文字按钮点击事件
     *
     * @param text
     * @param onClickListener
     */
    public void setRightText(String text, View.OnClickListener onClickListener) {
        mActionBarView.setRightText(text, onClickListener);
    }


    /**
     * 数据拉去成功的时候调用
     *
     * @param smart
     * @param isRefresh
     * @param size
     */
    public void RefreshSuccess(SmartRefreshLayout smart, boolean isRefresh, int size) {
        smart.finishRefresh();
        smart.finishLoadmore();
        if (isRefresh) {
            page = 1;
        }
        if (size < 10) {
            add = false;
        } else {
            add = true;
            page++;
        }
        smart.setEnableLoadmore(add);

    }

    /**
     * 初始化刷新状态
     *
     * @param smart
     */
    public void RefreshSuccessInit(SmartRefreshLayout smart, boolean isRefresh) {
        smart.finishRefresh();
        smart.finishLoadmore();
        if (isRefresh) {
            page = 1;
            add = true;
        }
    }
}
