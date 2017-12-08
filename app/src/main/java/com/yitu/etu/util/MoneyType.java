package com.yitu.etu.util;

/**
 * @className:MoneyType
 * @description:金钱的转换类型
 * @author: JIAMING.LI
 * @date:2016年10月24日 16:28
 */
public class MoneyType {

    /**
     * 正常转换，只进行四舍五入
     */
    public static final int TYPE_NORMAL=0;
    /**
     * 分转换成元
     */
    public static final int TYPE_YUAN=1;
    /**
     * 元转换成万
     */
    public static final int TYPE_YUAN_TO_WAN=2;
    /**
     * 分直接转换成万元
     */
    public static final int TYPE_WAN=3;
    /**
     * 元转分
     */
    public static final int TYPE_YUAN_TO_FEN=4;
    /**
     * 万转分
     */
    public static final int TYPE_WAN_TO_FEN=5;
}
