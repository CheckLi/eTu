package com.yitu.etu.tools.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @className:IntegerTypeAdapter
 * @description:
 * @author: JIAMING.LI
 * @date:2017年09月29日 15:49
 */
public class IntegerTypeAdapter extends TypeAdapter<Integer> {
    @Override
    public void write(JsonWriter out, Integer value) throws IOException {
        System.out.println("解析数值："+value);
        out.value(value.toString());
    }

    @Override
    public Integer read(JsonReader in) throws IOException {
        try {
            System.out.println("解析数值："+in.nextString());
            return 1001;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
