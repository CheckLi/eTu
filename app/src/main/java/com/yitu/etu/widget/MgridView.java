package com.yitu.etu.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.GridView;

import com.yitu.etu.Iinterface.ImageSelectListener;
import com.yitu.etu.ui.activity.BaseActivity;
import com.yitu.etu.ui.adapter.ChooseImageAdapter;

import java.util.List;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/21.
 */
public class MgridView extends GridView {
    public static final int REQUEST_LIST_CODE = 0;
    public static final int REQUEST_CAMERA_CODE = 1;
    private int curPosition = 0;
    private boolean isRepleace = false;
    private ChooseImageAdapter adapter;

    public MgridView(Context context) {
        super(context);
        init();
    }

    public MgridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MgridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    private void init() {
        adapter = new ChooseImageAdapter(getContext());
        setAdapter(adapter);
        adapter.setListener(new ImageSelectListener() {
            @Override
            public void select(int position) {
                curPosition = position;
                if (position < adapter.getCount() - 1) {
                    isRepleace = true;
                }else{
                    isRepleace=false;
                }
                ((BaseActivity) getContext()).Single(false);
            }
        });
    }
    public ChooseImageAdapter getChooseImageAdapter(){
            return adapter;
    }
    public void add(String path) {
        if (!isRepleace) {
            adapter.addData(path);
        } else {
            adapter.replace(curPosition, path);
        }
    }

    public void getParams() {
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
    }

    public void onActivityResult(int resultCode, int requestCode, Intent data) {
        if (requestCode == REQUEST_LIST_CODE && resultCode == ((BaseActivity) getContext()).RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            try {
                add(pathList.get(0));
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_CAMERA_CODE && resultCode == ((BaseActivity) getContext()).RESULT_OK && data != null) {
            String path = data.getStringExtra("result");
            add(path);
        }
    }

    public String getImagePutString() {
        return adapter.getPutString();
    }

    /**
     * 控制ADD显示
     * @param isShow true显示
     */
    public void showAdd(boolean isShow){
        adapter.showAdd(isShow);
    }
}
