package com.yitu.etu.util.system;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * @className:PermissionUtils
 * @description:
 * @author: JIAMING.LI
 * @date:2017年01月04日 09:36
 */
public class PermissionUtils {
    /**
     * 小米的权限设置界面
     *
     * @param a
     */
    public static void openPermissionEdit(Activity a) {
        if (a == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", a.getPackageName(), null);
        intent.setData(uri);
        a.startActivity(intent);
    }
}
