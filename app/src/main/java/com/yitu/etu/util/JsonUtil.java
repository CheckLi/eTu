package com.yitu.etu.util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;

/**
 * @className:JsonUtil
 * @description:json对象的转换与创建
 * @author: JIAMING.LI
 * @date:2016年10月24日 09:42
 */
public class JsonUtil {

    public static JsonUtil getInstance() {
        return new JsonUtil();
    }

    /**
     * 使用gson进行bean文件的封装
     * 获取本地存储的数据并转换成相应的bean
     *
     * @param name 本地的存储名字
     * @param cls  实体类文件
     * @return
     */
    public <T> T getJsonLocalBean(String name, Class<T> cls) {
        T bean = null;
        try {
            String json = getStrValue(name);
            if (!TextUtils.isEmpty(json)) {
                Gson gson = GsonUtil.gson();
                JsonReader reader = new JsonReader(new StringReader(json));
                reader.setLenient(true);
                bean = gson.fromJson(reader, cls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 使用gson进行bean文件的封装
     * 将数据转换成相应的bean
     *
     * @param json json数据
     * @param cls  实体类文件
     * @return
     */
    public <T> T getJsonBean(String json, Class<T> cls) {
        if(TextUtils.isEmpty(json)){
            return null;
        }
        T bean = null;
        try {
            if (!TextUtils.isEmpty(json)) {
                Gson gson =  GsonUtil.gson();
                JsonReader reader = new JsonReader(new StringReader(json));
                reader.setLenient(true);
                bean = gson.fromJson(reader, cls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 给一个bean对象，将转换成string返回
     * @param jsonBean json对象
     * @return
     */
    public String getJsonString(Object jsonBean) {
        String gsonString = null;
        try {
            if (jsonBean != null) {
                Gson gson = GsonUtil.gson();
                gsonString = gson.toJson(jsonBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gsonString;
    }

    /**
     * 保存数据到本地
     * @param jsonBean
     */
    public boolean saveJsonToLocal(String key,Object jsonBean){
        try{
            String saveString=getJsonString(jsonBean);
            PrefrersUtil.getInstance().saveValue(key,saveString);
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 获取String数据
     * @param name
     * @return
     */
    private String getStrValue(String name) {
        try{
            return PrefrersUtil.getInstance().getValue(name,"");
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
