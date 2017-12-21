package com.yitu.etu.util;

import android.util.Base64;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @className:FileUtil
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月21日 14:23
 */
public class FileUtil {

    public static String GetImageStr(String imgFilePath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;

        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 对字节数组Base64编码
        return "data:image/=" + imgFilePath.substring(imgFilePath.lastIndexOf(".") + 1) + ";base64," + Base64.encodeToString(data, Base64.DEFAULT);// 返回Base64编码过的字节数组字符串
    }

}
