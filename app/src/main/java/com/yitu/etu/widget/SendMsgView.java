package com.yitu.etu.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yitu.etu.R;
import com.yitu.etu.util.ToastUtil;

/**
 * TODO this class desription here
 * <p>
 * Created by deng meng on 2017/12/27.
 */
public class SendMsgView extends FrameLayout{
    SendMsgListener sendMsg;
    public SendMsgView(@NonNull Context context) {
        super(context);
        init();
    }

    public SendMsgView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SendMsgView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    void init(){
        View v=LayoutInflater.from(getContext()).inflate(R.layout.layout_send_msg,null);
        addView(v);
        EditText editText=(EditText)v.findViewById(R.id.text);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEND){
                    if(sendMsg!=null){
                        String text=v.getText().toString();
                        if(text.equals("")){
                            ToastUtil.showMessage("请输入文字");
                        }
                        else{
                            v.setText("");
                            sendMsg.send(text);

                        }
                    }
                }
                return false;
            }
        });
    }

    public void setSendMsg(SendMsgListener sendMsg) {
        this.sendMsg = sendMsg;
    }

    public interface  SendMsgListener{
        public void send(String text);
    }
}
