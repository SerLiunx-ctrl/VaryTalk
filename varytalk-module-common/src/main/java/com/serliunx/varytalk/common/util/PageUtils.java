package com.serliunx.varytalk.common.util;

import com.github.pagehelper.PageHelper;
import com.serliunx.varytalk.common.result.Result;

import java.util.List;

/**
 * 分页工具类
 * @author SerLiunx
 * @since 1.0
 */
public final class PageUtils {

    private PageUtils(){}

    /**
     * 当前线程(请求)开始分页、从请求头中读取页码及页面尺寸.
     */
    public static void startPage(){
        int pageNum = 1, pageSize = 10;
        String pageNumString = ServletUtils.getParameter("pageNum");
        String pageSizeString = ServletUtils.getParameter("pageSize");
        try {
            pageNum = Integer.parseInt(pageNumString);
            pageSize = Integer.parseInt(pageSizeString);
        }catch (Exception e){
            //
        }
        PageHelper.startPage(pageNum, pageSize);
    }

    /**
     * 生成分页后的结果
     * @param list 列表
     * @return 结果
     */
    public static <T> Result page(List<? extends T> list){
        return Result.pageResult(list);
    }
}
