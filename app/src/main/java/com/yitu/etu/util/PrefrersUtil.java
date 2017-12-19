package com.yitu.etu.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.yitu.etu.EtuApplication;

import java.util.Set;

/**
 * @className:PrefresUtil
 * @description: 存储数据的工具类
 * @author: JIAMING.LI
 * @date:2016年10月24日 10:38
 */
public class PrefrersUtil {
    public static final String PREFERENCES_FILE = "hz";

    private static PrefrersUtil PREFERSUTIL;
    private static SharedPreferences cache;
    private SharedPreferences.Editor editor;

    public static String PREFERENCE_NAME = "VersionUpdate";

    private PrefrersUtil(Context fra_act) {
        cache = fra_act.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        editor = cache.edit();
    }

    public static PrefrersUtil getInstance() {
        if (PREFERSUTIL == null) {
            PREFERSUTIL = new PrefrersUtil(EtuApplication.getInstance());
        }
        return PREFERSUTIL;
    }

    /**
     * 存储数据
     * @param object
     */
    public void saveValue(String key,Object object){
        try{
            if(object instanceof Float) {
                editor.putFloat(key, (Float) object);
            }else if(object instanceof Integer) {
                editor.putInt(key, (Integer) object);
            }else if(object instanceof String) {
                editor.putString(key, (String) object);
            }else if(object instanceof Long) {
                editor.putLong(key, (Long) object);
            }else if(object instanceof Boolean) {
                editor.putBoolean(key, (Boolean) object);
            }else if(object instanceof Set) {
                editor.putStringSet(key, (Set<String>) object);
            }
            editor.apply();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 保存class数据
     * @param key
     * @param object
     */
    public void saveClass(String key,Object object){
        String json=JsonUtil.getInstance().getJsonString(object);
        saveValue(key,json);
    }

    public <T> T getClass(String key,Class<T> tClass){
        return JsonUtil.getInstance().getJsonLocalBean(key,tClass);
    }

    public void remove(String key){
        editor.remove(key);
        editor.apply();
    }

    /**
     * 从本地获取string数据
     */
    public String getValue(String key,String def){
       return cache.getString(key,def);
    }
    /**
     * 从本地获取Long数据
     */
    public Long getValue(String key,Long def){
        return cache.getLong(key,def);
    }
    /**
     * 从本地获取Integer数据
     */
    public Integer getValue(String key,Integer def){
        return cache.getInt(key,def);
    }
    /**
     * 从本地获取Boolean数据
     */
    public Boolean getValue(String key,Boolean def){
        return cache.getBoolean(key,def);
    }
    /**
     * 从本地获取Float数据
     */
    public Float getValue(String key,Float def){
        return cache.getFloat(key,def);
    }

    /**
     * 从本地获取Set<String>数据
     */
    public Set<String> getValue(String key,Set<String> def){
        return cache.getStringSet(key,def);
    }
}
