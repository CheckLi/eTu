package com.yitu.etu.tools;

import com.yitu.etu.BuildConfig;
import com.yitu.etu.entity.GeneralRequestParams;
import com.yitu.etu.util.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @className:Http
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月16日 15:13
 */
public class Http {


    public static RequestCall post(String url, HashMap<String, String> params, ArrayList<PostFileParams> postFileparams, Callback callback, boolean needToken) {
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(url);
        if (params != null) {
            Iterator iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                String val = (String) entry.getValue();
                postFormBuilder.addParams(key, val);
            }
        }
        if (postFileparams != null) {
            for (PostFileParams postFileparam :
                    postFileparams) {
                postFormBuilder.addFile(postFileparam.paramName, postFileparam.fileName, new File(postFileparam.path));

            }
        }
        if (needToken) {
            for (Map.Entry<String,String> map: GeneralRequestParams.getParams().entrySet()){
                postFormBuilder.addParams(map.getKey(),map.getValue());
            }
        }
        if (BuildConfig.DEBUG) {
            LogUtil.e("response:","请求参数"+params.toString()+"\n通用参数"+GeneralRequestParams.getParams().toString());
        }
        RequestCall postFormRequest = postFormBuilder.build();
        postFormRequest.execute(callback);
//      postFormRequest.cancel();//关闭连接
        return postFormRequest;
    }

    public static RequestCall postFile(String url, HashMap<String, String> params, ArrayList<PostFileParams> postFileparams, Callback callback, boolean needToken) {
        return post(url, params, postFileparams, callback, needToken);
    }

    public static RequestCall postFile(String url, HashMap<String, String> params, ArrayList<PostFileParams> postFileparams, Callback callback) {
        return post(url, params, postFileparams, callback, true);
    }

    public static RequestCall post(String url, HashMap<String, String> params, Callback callback, boolean needToken) {
        return post(url, params, null, callback, needToken);
    }

    public static RequestCall post(String url, HashMap<String, String> params, Callback callback) {
        return post(url, params, null, callback, true);
    }

    class PostFileParams {
        String fileName;
        String path;
        String paramName;

        public PostFileParams(String fileName,
                              String paramName,
                              String path) {
            this.path = path;
            this.fileName = fileName;
            this.paramName = paramName;
            if (fileName == null || fileName.equals("")) {
                this.fileName = "empty";
            }
        }

        public PostFileParams(String paramName,
                              String path) {
            this("", paramName, path);

        }
    }
}
