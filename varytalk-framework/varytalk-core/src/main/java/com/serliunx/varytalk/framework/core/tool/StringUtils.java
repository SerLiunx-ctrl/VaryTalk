package com.serliunx.varytalk.framework.core.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @author SerLiunx
 * @since 1.0
 */
public final class StringUtils {

    private StringUtils(){}

    /**
     * 驼峰命名法转下划线(首字母小写)
     * <li> StringUtils -> string_utils
     * @param source 源字符串
     * @return 转换完成后的字符串
     */
    public static String camelToUnderline(String source){
        Pattern pattern = Pattern.compile("(?<=[a-z])([A-Z])");
        Matcher matcher = pattern.matcher(source);
        return matcher.replaceAll("_$1").toLowerCase();
    }

    /**
     * 占位符替换
     * <li> 使用替换的方式对原始key中所存在的占位符进行替换,
     * 如 -> redis:park:lock:{arg1}, 替换后 -> redis:park:lock:1
     * <li> 原始key没有任何占位符可替换时将没有任何效果
     * <li> 存在占位符请务必使用{}包裹 如{name}, {park_id}, 名称不限制. 按照顺序替换
     * <li> 由于参数的获取方式是调用其toString方法、所以建议使用基本类型及其包装类或者重写toString方法
     * @param rawText 原始文本
     * @param args 参数
     * @return 替换后的文本
     */
    public static String applyPlaceholders(String rawText, Object...args){
        if (args == null) {
            return rawText;
        }
        for (Object arg : args) {
            String placeholder = getFirstPlaceholder(rawText);
            rawText = rawText.replace(placeholder, arg.toString());
        }
        return rawText;
    }

    private static String getFirstPlaceholder(String key){
        int left = key.indexOf("{");
        int right = key.indexOf("}");
        if(left == -1 || right == -1){
            return key;
        }
        return key.substring(left, right + 1);
    }
}
