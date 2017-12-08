package com.yitu.etu.util;

import android.text.SpannableString;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * @className:ViewUtils
 * @description:为view设置text值
 * @author: JIAMING.LI
 * @date:2016年08月31日 10:30
 */
public class ViewUtils {

    /**
     * 给view设置text内容,如果等于需要hide的值则隐藏，默认等于空也隐藏
     * @param view      需要设置内容的view
     * @param content   为view设置的内容
     * @param hide      等于这个值的时候隐藏
     */
    public static void setText(View view, String content, String...hide){
        if(isHide(content,hide)){
            view.setVisibility(View.GONE);
            return;
        }else{
            view.setVisibility(View.VISIBLE);
        }
        if(view instanceof TextView){
            ((TextView)view).setText(getText(content));
        }else if(view instanceof Button){
            ((Button)view).setText(getText(content));
        }else if(view instanceof CheckBox){
            ((CheckBox)view).setText(getText(content));
        }else if(view instanceof RadioButton){
            ((RadioButton)view).setText(getText(content));
        }else if(view instanceof EditText){
            ((EditText)view).setText(getText(content));
        }
    }

    /**
     * 给view设置text内容
     * @param view      需要设置内容的view
     * @param content   为view设置的内容
     */
    public static void setText(View view, String content){
        if(view instanceof TextView){
            ((TextView)view).setText(getText(content));
        }else if(view instanceof Button){
            ((Button)view).setText(getText(content));
        }else if(view instanceof CheckBox){
            ((CheckBox)view).setText(getText(content));
        }else if(view instanceof RadioButton){
            ((RadioButton)view).setText(getText(content));
        }else if(view instanceof EditText){
            ((EditText)view).setText(getText(content));
        }
    }

    /**
     * 给view设置SpannableString内容，用于文字片段格式修改
     * @param view      需要设置内容的view
     * @param content   为view设置的内容
     */
    public static void setText(View view, SpannableString content){
        if(view instanceof TextView){
            ((TextView)view).setText(getText(content));
        }else if(view instanceof Button){
            ((Button)view).setText(getText(content));
        }else if(view instanceof CheckBox){
            ((CheckBox)view).setText(getText(content));
        }else if(view instanceof RadioButton){
            ((RadioButton)view).setText(getText(content));
        }else if(view instanceof EditText){
            ((EditText)view).setText(getText(content));
        }
    }

    /**
     * 给view设置text内容
     * @param view      需要设置内容的view
     * @param content   为view设置的内容
     */
    public static void setText(View view, String content, String defaultString){
        if(view instanceof TextView){
            ((TextView)view).setText(getText(content,defaultString));
        }else if(view instanceof Button){
            ((Button)view).setText(getText(content,defaultString));
        }else if(view instanceof CheckBox){
            ((CheckBox)view).setText(getText(content,defaultString));
        }else if(view instanceof RadioButton){
            ((RadioButton)view).setText(getText(content,defaultString));
        }else if(view instanceof EditText){
            ((EditText)view).setText(getText(content,defaultString));
        }
    }

    /**
     * 判断content是不是空，是空返回单引号
     * @param content
     * @return
     */
    public static String getText(String content){
        if(TextUtils.isEmpty(content)){
            return "";
        }else{
            return content;
        }
    }

    /**
     * 判断content是不是空，是空返回单引号
     * @param content
     * @return
     */
    public static SpannableString getText(SpannableString content){
        if(TextUtils.isEmpty(content)){
            return new SpannableString("");
        }else{
            return content;
        }
    }

    /**
     * 判断content是不是空，是空返回单引号
     * @param content
     * @return
     */
    private static String getText(String content, String defaultStirng){
        if(TextUtils.isEmpty(content)){
            return TextUtils.isEmpty(defaultStirng)?"":defaultStirng;
        }else{
            return content;
        }
    }

    /**
     * 判断字符是否属于需要隐藏的数据
     * @param content   填充值
     * @param hide      对象值，参照对象，属于此参照对象的则返回true
     * @return
     */
    private static boolean isHide(String content,String...hide){
        if(TextUtils.isEmpty(content)){
            return true;
        }
        if(hide!=null) {
            for (String s : hide) {
                if (s.equals(content)) {
                    return true;
                }
            }
        }
        return false;
    }
}
