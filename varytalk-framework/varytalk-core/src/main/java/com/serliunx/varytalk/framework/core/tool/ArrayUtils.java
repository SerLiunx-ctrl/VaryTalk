package com.serliunx.varytalk.framework.core.tool;

/**
 * 数组工具类
 * @author SerLiunx
 * @since 1.0
 */
public final class ArrayUtils {

    private ArrayUtils(){}

    /**
     * 数组转换, String -> Long
     * @param source 源数组
     * @return 目标数组
     */
    public static Long[] stringToLong(String[] source){
        Long[] array = new Long[source.length];
        for (int i = 0; i < source.length; i++) {
            array[i] = Long.valueOf(source[i]);
        }
        return array;
    }
}