package com.yitu.etu.system.system;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.yitu.etu.BuildConfig;

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
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        i.setComponent(componentName);
        i.putExtra("extra_pkgname", a.getPackageName());
        try {
            a.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            getAppDetailSettingIntent(a);
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private static void gotoMeizuPermission(Activity a) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        try {
            a.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            getAppDetailSettingIntent(a);
        }
    }

    /**
     * 华为的权限管理页面
     */
    private static void gotoHuaweiPermission(Activity a) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            a.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            getAppDetailSettingIntent(a);
        }

    }

    /**
     * 跳转到权限设置界面
     */
    private static void getAppDetailSettingIntent(Context context){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(Build.VERSION.SDK_INT >= 9){
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if(Build.VERSION.SDK_INT <= 8){
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings","com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(intent);
    }

    /**
     * 打开权限设置页面
     * @param a
     */
    public static void statrtPermission(Activity a){
        if(Rom.isMiui()){
            openPermissionEdit(a);
        }else if(Rom.isFlyme()){
            gotoMeizuPermission(a);
        }else if(Rom.isEmui()){
            gotoHuaweiPermission(a);
        }else{
            getAppDetailSettingIntent(a);
        }
    }
}
