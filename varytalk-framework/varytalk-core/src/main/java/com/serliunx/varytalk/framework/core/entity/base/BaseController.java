package com.serliunx.varytalk.framework.core.entity.base;

import com.serliunx.varytalk.framework.core.entity.result.Result;
import com.serliunx.varytalk.framework.core.tool.PageUtils;

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
        PageUtils.startPage();
    }

    protected <T> Result page(List<? extends T> list){
       return PageUtils.page(list);
    }
}
