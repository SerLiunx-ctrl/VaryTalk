package com.serliunx.varytalk.common.result;

import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResult<T>{

    private Integer pageNum;
    private Integer pageSize;
    private Long actualSize;
    private Integer pages;
    private Long total;
    private List<T> rows;

    public PageResult(Integer pageNum, Integer pageSize, Long actualSize, Integer pages, Long total, List<T> rows) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.actualSize = actualSize;
        this.pages = pages;
        this.total = total;
        this.rows = rows;
    }

    public static <T> PageResult<T> page(PageInfo<T> pageInfo){
        return new PageResult<>(pageInfo.getPageNum(), pageInfo.getPageSize(),
                (long) pageInfo.getSize(), pageInfo.getPages(), pageInfo.getTotal(),pageInfo.getList());
    }
}
