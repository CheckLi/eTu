package com.yitu.etu.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @className:TextUtils
 * @description:文本工具类
 * @author: JIAMING.LI
 * @date:2016年07月07日 11:27
 */
public class TextUtils {
    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串是否为空
     *
     * @return
     */
    public static boolean isEmpty(String content) {
        if (content == null || content.equals("null") || android.text.TextUtils.isEmpty(content.trim())) {
            return true;
        }
        /**
         * 如果需要单元测试，取消此注释，并把上面注释
         */
//        if(content==null){
//            return true;
//        }
        return false;
    }

    /**
     * 判断字符串是否为空
     *
     * @return
     */
    public static boolean isEmpty(CharSequence text) {
        if (text == null || text.equals("null") || android.text.TextUtils.isEmpty(text)) {
            return true;
        }
        /**
         * 如果需要单元测试，取消此注释，并把上面注释
         */
//        if(text==null){
//            return true;
//        }
        return false;
    }

    /**
     * 判断内容是否为空，为空则返回默认值
     *
     * @return
     */
    public static String getText(String text, String defaultString) {
        if (isEmpty(text)) {
            return defaultString;
        } else {
            return text;
        }
    }
}
