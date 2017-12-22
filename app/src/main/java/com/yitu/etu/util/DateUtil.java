package com.yitu.etu.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @className:DateUtil
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月22日 14:27
 */
public class DateUtil {
    /**
     * 获取时间
     *
     * @param time
     * @param format
     * @return
     */
    public static String getTime(String time, String format) {
        time = TextUtils.isEmpty(time) ? "0" : time;
        format = TextUtils.isEmpty(format) ? "yyyy-MM-dd HH:mm:ss" : format;
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return formatter.format(new Date(Long.parseLong(time) * 1000L));
        } catch (Exception e) {
            e.printStackTrace();
            return time;
        }

    }
}
