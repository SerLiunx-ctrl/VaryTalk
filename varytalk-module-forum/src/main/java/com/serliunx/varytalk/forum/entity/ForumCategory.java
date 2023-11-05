package com.serliunx.varytalk.forum.entity;

import com.serliunx.varytalk.common.base.BaseEntity;

/**
 * 分区实体
 * @author SerLiunx
 * @since 1.0
 */
public class ForumCategory extends BaseEntity {

    private Long id;
    private String categoryName;
    private Integer displayOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
