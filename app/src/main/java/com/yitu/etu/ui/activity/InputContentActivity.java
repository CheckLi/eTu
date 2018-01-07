package com.yitu.etu.ui.activity;

import android.content.Intent;
import android.view.View;

import com.yitu.etu.R;
import com.yitu.etu.widget.SendMsgView;

import org.jetbrains.annotations.NotNull;

public class InputContentActivity extends BaseActivity {


    @Override
    public int getLayout() {
        return R.layout.dialog_send_msg;
    }

    @Override
    public void initActionBar() {


    }

    @Override
    public void initView() {
        mActionBarView.setVisibility(View.GONE);
       ((SendMsgView)findViewById(R.id.send_msg)) .setSendMsg(new SendMsgView.SendMsgListener() {
           @Override
           public void send(@NotNull String text) {
               Intent intent=new Intent();
               intent.putExtra("text",text);
               setResult(RESULT_OK,intent);
               finish();
           }
       });

    }

    @Override
    public void getData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(-1,R.anim.actionsheet_dialog_out);
    }
}
