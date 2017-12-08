package com.yitu.etu.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yitu.etu.tools.gson.DoubleDefault0Adapter;
import com.yitu.etu.tools.gson.FloatDefault0Adapter;
import com.yitu.etu.tools.gson.IntegerDefault0Adapter;
import com.yitu.etu.tools.gson.ListDefaultAdapter;
import com.yitu.etu.tools.gson.LongDefault0Adapter;

import java.util.List;

/**
 * @className:GsonTool
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月08日 18:45
 */
public class GsonUtil {
    public GsonUtil() {

    }

    public static GsonUtil getInstance() {
        return new GsonUtil();
    }

    public static Gson gson() {
        /**
         * 以下是防止解析的时候崩溃
         */
        return new GsonBuilder()
                .registerTypeHierarchyAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeHierarchyAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeHierarchyAdapter(int.class, new IntegerDefault0Adapter())
                .registerTypeHierarchyAdapter(Float.class, new FloatDefault0Adapter())
                .registerTypeHierarchyAdapter(float.class, new FloatDefault0Adapter())
                .registerTypeHierarchyAdapter(Double.class, new DoubleDefault0Adapter())
                .registerTypeHierarchyAdapter(double.class, new DoubleDefault0Adapter())
                .registerTypeHierarchyAdapter(Long.class, new LongDefault0Adapter())
                .registerTypeHierarchyAdapter(long.class, new LongDefault0Adapter())
                .registerTypeHierarchyAdapter(List.class, new ListDefaultAdapter())
                .create();
    }
}
