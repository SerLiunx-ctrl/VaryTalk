package com.serliunx.varytalk.common.base;

import com.github.pagehelper.PageHelper;
import com.serliunx.varytalk.common.result.Result;
import com.serliunx.varytalk.common.util.ServletUtils;

import java.util.List;

public class BaseController {

    protected Result success(){
        return Result.success();
    }

    protected Result success(Object o){
        return Result.success(o);
    }

    protected Result success(String msg){
        return Result.success(msg);
    }

    protected Result success(Object o, String msg){
        return Result.success(o, msg);
    }

    protected Result fail(){
        return Result.fail();
    }

    protected Result fail(String msg){
        return Result.fail(msg);
    }

    protected void startPage(){
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

    protected <T> Result page(List<? extends T> list){
        return Result.pageResult(list);
    }
}
