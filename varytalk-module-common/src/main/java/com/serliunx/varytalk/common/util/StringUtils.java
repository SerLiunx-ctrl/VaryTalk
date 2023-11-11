package com.serliunx.varytalk.common.util;

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
}
