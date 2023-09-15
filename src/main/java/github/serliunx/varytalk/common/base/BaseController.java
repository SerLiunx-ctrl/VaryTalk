package github.serliunx.varytalk.common.base;

import com.github.pagehelper.PageInfo;
import github.serliunx.varytalk.common.result.Result;

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

    protected <T> Result page(PageInfo<T> pageInfo){
       return Result.pageResult(pageInfo);
    }
}
