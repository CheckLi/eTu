package com.yitu.etu.entity;

import com.google.gson.annotations.SerializedName;

/**
 * @className:ErrorBean
 * @description:
 * @author: JIAMING.LI
 * @date:2017年09月04日 18:39
 */
public class ErrorBean {

    /**
     * msgtype : text
     * text : {"content":"我就是我, 是不一样的烟火"}
     */

    @SerializedName("msgtype")
    public String msgtype;
    @SerializedName("text")
    public TextBean text;

    public static class TextBean {
        /**
         * content : 我就是我, 是不一样的烟火
         */

        @SerializedName("content")
        public String content;
    }
}
