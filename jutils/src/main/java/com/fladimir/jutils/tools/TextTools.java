package com.fladimir.jutils.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by NingJiang on 2017/6/7.
 * Class Note:
 */

public class TextTools {

    /**
     * 判断一个字符串是否是字母
     *
     * @param str
     * @return
     */
    public static boolean isLetter(String str) {
        boolean flag = false;
        if (str != null) {
            if ((str.charAt(0) >= 'A' && str.charAt(0) <= 'Z')
                    || (str.charAt(0) >= 'a' && str.charAt(0) <= 'z')) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 字符串转为Utf-8
     *
     * @param str 传入String
     * @return
     * @throws UnsupportedEncodingException 提示异常
     */
    public static String toUtf8(String str) {
        try {
            return URLEncoder.encode(str, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 设置隐藏字符
     *
     * @param text
     * @return
     */
    public static String HideText(String text) {
        int lengtn = text.length();
        if (lengtn == 1) {
            return "*";
        } else if (lengtn == 2) {
            return text.substring(0, 1) + "*";
        } else {
            return text.substring(0, 1) + "**" + text.substring(text.length() - 1);
        }
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }

    /**
     * 判断str null,"","null" 均视为空.
     *
     * @param str 字符
     * @return 结果 boolean
     */
    public static boolean isNotEmpty(String str) {
        boolean bool = true;
        if (str == null || "null".equals(str) || "".equals(str)) {
            bool = false;
        } else {
            bool = true;
        }
        return bool;
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */

    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

            return true;
        }
        return false;

    }

}
