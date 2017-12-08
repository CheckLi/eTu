package com.yitu.etu.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @className:MoneyUtils
 * @description: 金钱转换
 * @author: JIAMING.LI
 * @date:2016年10月19日 15:47
 */
public class MoneyUtil {


    public static MoneyUtil getInstance() {
        return new MoneyUtil();
    }


    /**
     * 获取金钱格式转化,如果money是空的，则返回0并保留指定小数位数，根据type不同，返回不同的
     * 金钱类型
     *
     * @param money    需要转换的金钱
     * @param decimals 保留的小数位数,最大不能超过八位
     * @param type     转换的类型  通过MoneyType来获取转换类型
     * @return
     */
    public String getMoney(String money, int decimals, int type) {
        decimals = decimals < 0 ? 0 : decimals > 8 ? 8 : decimals;
        if (!TextUtils.isEmpty(money)) {
            switch (type) {
                case MoneyType.TYPE_NORMAL://普通转换，只进行了四舍五入
                    return getMoneyDecimalUp(money, decimals);
                case MoneyType.TYPE_YUAN://分转元
                    return getYuan(money, decimals);
                case MoneyType.TYPE_WAN://分转万
                    return getWan(money, decimals);
                case MoneyType.TYPE_YUAN_TO_WAN://元转万
                    return getMoneyDecimalUp(money, decimals);
                case MoneyType.TYPE_YUAN_TO_FEN://元转分
                    return getFen(money, decimals);
                case MoneyType.TYPE_WAN_TO_FEN://万转分
                    return getFen(getFen(money, decimals),decimals);
                default:
                    break;
            }
        }
        return getDefaultMoney(decimals);
    }


    /**
     * 保留指定小数位数的金额，并采舍入模式
     *
     * @param money    需要舍入的金钱
     * @param decimals 保留小数位数
     * @return
     */
    public String getMoneyDecimalUp(String money, int decimals) {
        try {
            BigDecimal bg = new BigDecimal(money).setScale(decimals, BigDecimal.ROUND_HALF_UP);
            return bg.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getDefaultMoney(decimals);
    }

    /**
     * 获取格式化金额，如000,000,000.000
     *
     * @param money    需要格式化的金额
     * @param decimals 保留的小数位数
     * @param unit     前缀如￥$等,如果填写为Null则会自动改为空格
     * @return
     */
    public String getMoneyFormat(String money, int decimals, String unit) {
        if (TextUtils.isEmpty(unit)) {
            unit = "";
        }
        if (!TextUtils.isEmpty(money)) {
            try {
                if (decimals <= 0) {
                    return new DecimalFormat("###,###").format(Double.parseDouble(money));
                }
                StringBuffer units = new StringBuffer("###,###.");
                DecimalFormat format = new DecimalFormat();
                for (int i = 0; i < decimals; i++) {
                    units.append("0");
                }
                format.applyPattern(units.toString());
                return unit + format.format(Double.parseDouble(money));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return unit + getDefaultMoney(2);
    }

    /**
     * 分转元，元转万都可以用
     *
     * @param money    转换的金额
     * @param decimals 保留的小数位数
     * @return
     */
    private String getYuan(String money, int decimals) {
        try {
            BigDecimal bd = new BigDecimal(money);
            BigDecimal result = bd.divide(new BigDecimal("100")).setScale(decimals, BigDecimal.ROUND_HALF_UP);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getDefaultMoney(2);
    }

    /**
     * 分转万
     *
     * @param decimals 保留的小数位数
     * @param money
     * @return
     */
    private String getWan(String money, int decimals) {
        try {
            BigDecimal bd = new BigDecimal(money);
            BigDecimal result = bd.divide(new BigDecimal("10000")).setScale(decimals, BigDecimal.ROUND_HALF_UP);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getDefaultMoney(2);
    }

    /**
     * 元转分
     *
     * @param decimals 保留的小数位数
     * @param money
     * @return
     */
    private String getFen(String money, int decimals) {
        try {
            BigDecimal bd = new BigDecimal(money);
            BigDecimal result = bd.multiply(new BigDecimal("100")).setScale(decimals, BigDecimal.ROUND_HALF_UP);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getDefaultMoney(2);
    }


    /**
     * 获取默认0的金额
     *
     * @return
     */
    private String getDefaultMoney(int decimals) {
        return getMoneyDecimalUp("0", decimals);
    }

}
