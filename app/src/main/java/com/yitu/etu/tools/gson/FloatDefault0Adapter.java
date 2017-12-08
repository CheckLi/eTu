package com.yitu.etu.tools.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * @className:IntegerDefault0Adapter
 * @description:需要的是float，返回空将值改成0.0
 * @author: JIAMING.LI
 * @date:2017年09月12日 16:21
 */
public class FloatDefault0Adapter implements JsonSerializer<Float>, JsonDeserializer<Float> {
    @Override
    public Float deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            if (json.getAsString().equals("") || json.getAsString().equals("null")) {//定义为int类型,如果后台返回""或者null,则返回0
                return 0.0f;
            }
        } catch (Exception ignore) {
        }
        try {
            return json.getAsFloat();
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    @Override
    public JsonElement serialize(Float src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}
