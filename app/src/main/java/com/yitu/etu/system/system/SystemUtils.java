package com.yitu.etu.system.system;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @className:SystemUtils
 * @description:
 * @author: JIAMING.LI
 * @date:2017年01月04日 09:24
 */
public class SystemUtils {
    public static final String SYS_EMUI = "sys_emui";
    public static final String SYS_MIUI = "sys_miui";
    public static final String SYS_FLYME = "sys_flyme";
    private final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
    private final String KEY_EMUI_VERSION = "ro.build.version.emui";
    private final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    public static SystemUtils getInstance(){
        return new SystemUtils();
    }

    /**
     * 判断是否是指定的系统
     * @param system 需要判断的系统
     * @return
     */
    public boolean isSystem(String system){
        if(system.equals(getSystem())){
            return true;
        }
        return false;
    }

    /**
     * 获取系统名称
     * @return
     */
    public  String getSystem(){
        String SYS = null;
        try {
            Properties prop= new Properties();
            prop.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
            if(prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null){
                SYS = SYS_MIUI;//小米
            }else if(prop.getProperty(KEY_EMUI_API_LEVEL, null) != null
                    ||prop.getProperty(KEY_EMUI_VERSION, null) != null
                    ||prop.getProperty(KEY_EMUI_CONFIG_HW_SYS_VERSION, null) != null){
                SYS = SYS_EMUI;//华为
            }else if(getMeizuFlymeOSFlag().toLowerCase().contains("flyme")){
                SYS = SYS_FLYME;//魅族
            }
        } catch (IOException e){
            e.printStackTrace();
            return SYS;
        }
        return SYS;
    }

    public  String getMeizuFlymeOSFlag() {
        return getSystemProperty("ro.build.display.id", "");
    }

    private  String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String)get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
        }
        return defaultValue;
    }

    /**
     * 判断是oppo
     * @return
     */
    public boolean isOppo(){
        return Rom.isOppo();
    }
}
