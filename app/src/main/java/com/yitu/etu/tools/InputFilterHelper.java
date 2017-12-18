package com.yitu.etu.tools;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.widget.Toast;

import com.yitu.etu.util.TextUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: HuangYunLin(283857754@qq.com)
 * Date: 2016-07-05
 * Time: 11:24
 * FIXME
 */
public final class InputFilterHelper {
    
    private static final String digits = "\\!！@＠#＃$¥%=^&*?()？～~><《》;+-_；:：|/\"\'\n\r";//特殊字符不允许输入
    //移动号码段
    //第一排移动，第二排联通，第三排电信，第四排虚拟运营
    private static final String dnseg[] = {"134", "135", "136", "137", "138", "139", "147", "150", "151", "152", "157", "158", "159", "178", "182", "183", "184", "187", "188"
            , "130", "131", "132", "145", "155", "156", "171", "175", "176", "185", "186",
            "133", "149", "153", "173", "177", "180", "181", "189",
            "170"};
    /**
     * 房屋面积保留小数
     */
    private static final int DECIMAL_DIGITS = 2; // 保留两位小数
    /**
     * 房屋面积保留整数
     */
    private static final int DIGITS = 3; // 保留3位整数
    
    private static Pattern mPattern = Pattern.compile("([0-9]|\\.)*");
    private static Pattern mChinese = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5',.。 ，!！；;:：?？《》<>@\n\r\n\t$#^&*()（）_=+-……*~、|/\\\\]");
    //小数点后的位数
    private static final int POINTER_LENGTH = 2;
    
    private static final String POINTER = ".";
    
    private static final String ZERO = "0";
    
    public static InputFilter getCashierInputFilter(final double maxValue) {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, //输入的文字w
                                       int start, int end,   //开始和结束位置
                                       Spanned dest, //当前显示的内容
                                       int dstart, int dend) {
                String sourceText = source.toString();
                String destText = dest.toString();
                
                //验证删除等按键
                if (TextUtils.isEmpty(sourceText)) {
                    return "";
                }
                
                Matcher matcher = mPattern.matcher(source);
                //已经输入小数点的情况下，只能输入数字
                if (destText.contains(POINTER)) {
                    if (!matcher.matches()) {
                        return "";
                    } else {
                        if (POINTER.equals(source)) {  //只能输入一个小数点
                            return "";
                        }
                    }
                    
                    //验证小数点精度，保证小数点后只能输入两位
                    int index = destText.indexOf(POINTER);
                    int length = dend - index;
                    
                    if (length > POINTER_LENGTH) {
                        return dest.subSequence(dstart, dend);
                    }
                } else {
                    //没有输入小数点的情况下，只能输入小数点和数字，但首位不能输入小数点和0
                    if (!matcher.matches()) {
                        return "";
                    } else {
                        if ((POINTER.equals(source) || ZERO.equals(source)) && TextUtils.isEmpty(destText)) {
                            return "";
                        }
                    }
                }
                
                //验证输入金额的大小
                double sumText = Double.parseDouble(destText + sourceText);
                if (sumText > maxValue) {
                    return dest.subSequence(dstart, dend);
                }
                
                return dest.subSequence(dstart, dend) + sourceText;
            }
        };
    }
    
    public static InputFilter getCashierMaxLengthInputFilter(final int maxValue) {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, //输入的文字w
                                       int start, int end,   //开始和结束位置
                                       Spanned dest, //当前显示的内容
                                       int dstart, int dend) {
                // 删除等特殊字符，直接返回
                if ("".equals(source.toString()) || ".".equals(source.toString())) {
                    return null;
                }
                String dValue = dest.toString();
                String[] splitArray = dValue.split("\\.");
                String integerValue = "";
                if (splitArray.length > 0) {
                    integerValue = splitArray[0];
                }
                if (splitArray.length > 1) {
                    String dotValue = splitArray[1];
                    int diff = dotValue.length() + 1 - DECIMAL_DIGITS;
                    if (diff > 0 && dValue.contains(".")) {
                        return dotValue.subSequence(start, end - diff);
                    }
                }
                if (integerValue.length() > 0) {
                    int diffOne = integerValue.length() + 1 - DIGITS;
                    int endIndex = end - diffOne;
                    //if(diffOne>0&&!dValue.contains(".") && endIndex > 0){
                    if (diffOne > 0 && !dValue.contains(".")) {
                        return integerValue = (String) integerValue.subSequence(start, end - diffOne);
                    }
                }
                return null;
            }
        };
    }

    /**
     *说明：控制数字输入位数,固定小数位数最多2位
     *@author: JIAMING.LI
     *修改时间 2017年12月04日上午10:41:01
     * @param maxLenght 控制整数位最大输入位数
     */
    public static InputFilter getNumberInputFilter(final int maxLenght) {
        return new InputFilter() {
            public CharSequence filter(CharSequence source, //输入的文字w
                                       int start, int end,   //开始和结束位置
                                       Spanned dest, //当前显示的内容
                                       int dstart, int dend) { //  当前开始和结束的位置
                // 删除等特殊字符，直接返回
                if ("".equals(source.toString()) || (TextUtils.isEmpty(dest) && ".".equals(source.toString()))) {
                    return "";
                }else if("\n".equals(source)||" ".equals(source)){
                    return "";
                }
                String dValue = dest.toString();
                if (dValue.contains(".")) {
                    String[] splitArray = dValue.split("\\.");
                    if (splitArray.length > 1 && splitArray[0].length() != dstart) {
                        Log.e("input", dValue.indexOf(".") + " " + splitArray[0].length());
                        String dotValue = splitArray[1];
                        if (dotValue.length() >= DECIMAL_DIGITS && dValue.contains(".")) {
                            return "";
                        }
                    } else if (splitArray.length > 1 && splitArray[0].length() >= maxLenght) {
                        return "";
                    }
                } else if (dValue.length() >= maxLenght && !source.equals(".")) {
                    return "";
                } else if (dValue.equals("0") && source.equals("0")) {
                    return "";
                } else if (dValue.equals("0") && !source.equals(".")) {
                    return "";
                }
                return null;
            }
        };
    }
    
    public static InputFilter getNumberInputFilter() {
        return getNumberInputFilter(3);
    }
    
    /**
     * 删除特殊字符
     *
     * @return
     */
    public static InputFilter specialCharacterInputFilter() {
        return new InputFilter() {
            public CharSequence filter(CharSequence source, //输入的文字w
                                       int start, int end,   //开始和结束位置
                                       Spanned dest, //当前显示的内容
                                       int dstart, int dend) { //  当前开始和结束的位置
                // 删除等特殊字符，直接返回
                if (digits.contains(source.toString())) {
                    return "";
                }
                return null;
            }
        };
    }
    
    
    /**
     * 过滤掉搜狗的emoji表情
     *
     * @return
     */
    public static InputFilter emojiInputFilter() {
        return new InputFilter() {
            private Set<String> filterSet = null;
            
            private void addUnicodeRangeToSet(Set<String> set, int start, int end) {
                if (set == null) {
                    return;
                }
                if (start > end) {
                    return;
                }
                
                for (int i = start; i <= end; i++) {
                    filterSet.add(new String(new int[]{
                            i
                    }, 0, 1));
                }
            }
            
            {
                filterSet = new HashSet<String>();
                
                // See http://apps.timwhitlock.info/emoji/tables/unicode
                
                // 1F601 - 1F64F
                addUnicodeRangeToSet(filterSet, 0x1F601, 0X1F64F);
                
                // 2702 - 27B0
                addUnicodeRangeToSet(filterSet, 0x2702, 0X27B0);
                
                // 1F680 - 1F6C0
                addUnicodeRangeToSet(filterSet, 0X1F680, 0X1F6C0);
                
                // 24C2 - 1F251
                addUnicodeRangeToSet(filterSet, 0X24C2, 0X1F251);
                
                // 1F600 - 1F636
                addUnicodeRangeToSet(filterSet, 0X1F600, 0X1F636);
                
                // 1F681 - 1F6C5
                addUnicodeRangeToSet(filterSet, 0X1F681, 0X1F6C5);
                
                // 1F30D - 1F567
                addUnicodeRangeToSet(filterSet, 0X1F30D, 0X1F567);
                
                // not included 5. Uncategorized
                
            }
            
            @Override
            public CharSequence filter(CharSequence source, int i, int i1, Spanned spanned, int i2, int i3) {
                if (filterSet.contains(source.toString())) {
                    return "";
                }
                return source;
            }
            
        };
    }
    
    
    /**
     * 判定是否是正确的身份证号码
     *
     * @return
     */
    public static InputFilter IdCardInput() {
        
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, //输入的文字w
                                       int start, int end,   //开始和结束位置
                                       Spanned dest, //当前显示的内容
                                       int dstart, int dend) {//  当前开始和结束的位置
                
                if (dend < 18 && TextUtils.isNumeric(source.toString())) {
                    return source;
                } else if (dend == 17 && (source.equals("x") || source.equals("X"))) {
                    return source.toString().toUpperCase();
                }
                return "";
            }
        };
    }
    
    
    /**
     * 判断是否是正确的手机号码
     *
     * @return
     */
    public static InputFilter phoneInput(final Context context) {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, //输入的文字w
                                       int start, int end,   //开始和结束位置
                                       Spanned dest, //当前显示的内容
                                       int dstart, int dend) {
//                Log.e("phoneInput", "filter() called with: " + "source = [" + source + "], start = [" + start + "], end = [" + end + "], dest = [" + dest + "], dstart = [" + dstart + "], dend = [" + dend + "]");
                if (dend < 12 && TextUtils.isNumeric(source.toString())) {
                    int size = dnseg.length, i;
                    if (dend == 3) {
                        for (i = 0; i < size; i++) {
                            if (dest.toString().equals(dnseg[i])) {
                                return source;
                            }
                        }
                        Toast.makeText(context, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    } else {
                        return source;
                    }
                }
                return "";
            }
        };
    }
    
    
    /**
     * 控制职能输入,防止输入表情以及特殊符号
     * 修改 jiaMingLi
     *
     * @return
     */
    public static InputFilter SpecialCharactersInputFilter() {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (TextUtils.isEmpty(dest) && (source.equals(" ") || source.equals("\n"))) {
                    return "";
                } else if (!TextUtils.isEmpty(dest) && dest.length() > 0 && dstart == 0 && (source.equals(" ") || source.equals("\n"))) {
                    return "";
                }
                for (int i = start; i < end; i++) {
                    Matcher matcher = mChinese.matcher(Character.toString(source.charAt(i)));
                    if (matcher.matches()) {
                        Log.e("TAG", Character.toString(source.charAt(i)) + "  " + source.toString());
                        return "";
                    }
                }
//                Matcher matcher = mChinese.matcher(source);
//                if (matcher.matches()) {
//                    Log.e("TAG",source.toString());
//                    return "";
//                }
                return null;
            }
        };
    }
}
