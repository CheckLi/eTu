package com.yitu;

import java.io.File;

/**
 * @className:Test
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月09日 17:50
 */
public class Test {
    @org.junit.Test
    public void test() {
        File file = new File("D:/360data/重要数据/桌面/项目图标/项目图标/12345");
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file1 = files[i];
            String path = file.getAbsolutePath();
            System.out.println(file1.getName() + " " + path + " " + (isChinese(file1.getName()) ? "有中文" : "无中文"));
//            if(file1.getName().lastIndexOf("@2x")>=0) {
                file1.renameTo(new File("D:/360data/重要数据/桌面/项目图标/项目图标/12345" + "/" + "icon" + (i+115) + ".png"));
//            }
        }
    }

    // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    // 完整的判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }
}

