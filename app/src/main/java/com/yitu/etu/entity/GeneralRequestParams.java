package com.yitu.etu.entity;

import com.yitu.etu.EtuApplication;

import java.util.HashMap;
import java.util.Map;

/**
 * @className:requestParams
 * @description:通用参数处理类
 * @author: JIAMING.LI
 * @date:2017年12月19日 11:04
 */
public class GeneralRequestParams {
    public static Map<String,String> getParams(){
        Map<String,String> params=new HashMap<>();
        if(EtuApplication.getInstance().isLogin()) {
            params.put("token", EtuApplication.getInstance().getUserInfo().getToken());
        }
        return params;
    }
}
