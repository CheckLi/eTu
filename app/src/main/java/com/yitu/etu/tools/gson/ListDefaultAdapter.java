package com.yitu.etu.tools.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * @className:ListDefaultAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2017年09月28日 12:11
 */
public class ListDefaultAdapter implements JsonDeserializer<List<?>> {
    @Override
    public List<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try{
            if (json.isJsonArray()) {
                //这里要自己负责解析了
                Gson newGson = getGson();
                return newGson.fromJson(json, typeOfT);
            } else {
                //和接口类型不符，返回空List
                return Collections.EMPTY_LIST;
            }
        }catch(Exception e){
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
    }

    public Gson getGson() {
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
                .create();
    }
}
