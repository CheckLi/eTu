package com.yitu.etu.tools;

import android.util.Log;

import com.google.gson.internal.$Gson$Types;
import com.yitu.etu.BuildConfig;
import com.yitu.etu.util.GsonUtil;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

public abstract class GsonCallback<T> extends Callback<T> {

    final Type type;

    @SuppressWarnings("unchecked")
    public GsonCallback() {
        this.type = getSuperclassTypeParameter(getClass());
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        T t = null;
        String str = response.body().string();
        if (BuildConfig.DEBUG) {
            Log.e("response:  ", str);
        }
        t = GsonUtil.gson().fromJson(str, getType());

        return t;
    }

    Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        } else {
            ParameterizedType parameterized = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }
    }

    public final Type getType() {
        return this.type;
    }
}

