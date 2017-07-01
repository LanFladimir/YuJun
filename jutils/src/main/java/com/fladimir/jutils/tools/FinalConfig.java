package com.fladimir.jutils.tools;

import java.util.regex.Pattern;

/**
 * Created by NingJiang on 2017/6/7.
 * Class Note:
 */

class FinalConfig {
    //验证邮件
    static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    //验证手机号
    static final String PHONE_PATTERN = "[1][358]\\d{9}";
    static final String PHONE_PATTERN_2 = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\\\d{8}$";
    //邮箱表达式
    final static Pattern email_pattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");

    //手机号表达式
    final static Pattern phone_pattern = Pattern.compile("^(13|15|18)\\d{9}$");

    //银行卡号表达式
    final static Pattern bankNo_pattern = Pattern.compile("^[0-9]{16,19}$");

    //座机号码表达式
    final static Pattern plane_pattern = Pattern.compile("^((\\(\\d{2,3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(\\-\\d{1,4})?$");

    //非零表达式
    final static Pattern notZero_pattern = Pattern.compile("^\\+?[1-9][0-9]*$");

    //数字表达式
    final static Pattern number_pattern = Pattern.compile("^[0-9]*$");

    //大写字母表达式
    final static Pattern upChar_pattern = Pattern.compile("^[A-Z]+$");

    //小写字母表达式
    final static Pattern lowChar_pattern = Pattern.compile("^[a-z]+$");

    //大小写字母表达式
    final static Pattern letter_pattern = Pattern.compile("^[A-Za-z]+$");

    //中文汉字表达式
    final static Pattern chinese_pattern = Pattern.compile("^[\u4e00-\u9fa5],{0,}$");

    //条形码表达式
    final static Pattern onecode_pattern = Pattern.compile("^(([0-9])|([0-9])|([0-9]))\\d{10}$");

    //邮政编码表达式
    final static Pattern postalcode_pattern = Pattern.compile("([0-9]{3})+.([0-9]{4})+");

    //IP地址表达式
    final static Pattern ipaddress_pattern = Pattern.compile("[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))");

    //URL地址表达式
    final static Pattern url_pattern = Pattern.compile("(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?");

    //用户名表达式
    final static Pattern username_pattern = Pattern.compile("^[A-Za-z0-9_]{1}[A-Za-z0-9_.-]{3,31}");

    //真实姓名表达式
    final static Pattern realnem_pattern = Pattern.compile("[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*");

    //匹配HTML标签,通过下面的表达式可以匹配出HTML中的标签属性。
    final static Pattern html_patter = Pattern.compile("<\\\\/?\\\\w+((\\\\s+\\\\w+(\\\\s*=\\\\s*(?:\".*?\"|'.*?'|[\\\\^'\">\\\\s]+))?)+\\\\s*|\\\\s*)\\\\/?>");

    //抽取注释,如果你需要移除HMTL中的注释，可以使用如下的表达式。
    final static Pattern notes_patter = Pattern.compile("<!--(.*?)-->");

    //查找CSS属性,通过下面的表达式，可以搜索到相匹配的CSS属性。
    final static Pattern css_patter = Pattern.compile("^\\\\s*[a-zA-Z\\\\-]+\\\\s*[:]{1}\\\\s[a-zA-Z0-9\\\\s.#]+[;]{1}");

    //提取页面超链接,提取html中的超链接。
    final static Pattern hyperlink_patter = Pattern.compile("(<a\\\\s*(?!.*\\\\brel=)[^>]*)(href=\"https?:\\\\/\\\\/)((?!(?:(?:www\\\\.)?'.implode('|(?:www\\\\.)?', $follow_list).'))[^\"]+)\"((?!.*\\\\brel=)[^>]*)(?:[^>]*)>");

    //提取网页图片,假若你想提取网页中所有图片信息，可以利用下面的表达式。
    final static Pattern image_patter = Pattern.compile("\\\\< *[img][^\\\\\\\\>]*[src] *= *[\\\\\"\\\\']{0,1}([^\\\\\"\\\\'\\\\ >]*)");

    //提取Color Hex Codes,有时需要抽取网页中的颜色代码，可以使用下面的表达式。
    final static Pattern color_patter = Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$");

    //文件路径及扩展名校验,验证windows下文件路径和扩展名（下面的例子中为.txt文件）
    final static Pattern route_patter = Pattern.compile("^([a-zA-Z]\\\\:|\\\\\\\\)\\\\\\\\([^\\\\\\\\]+\\\\\\\\)*[^\\\\/:*?\"<>|]+\\\\.txt(l)?$");
}
