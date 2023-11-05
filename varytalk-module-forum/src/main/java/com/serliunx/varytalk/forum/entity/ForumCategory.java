package com.serliunx.varytalk.forum.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.serliunx.varytalk.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 分区实体
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
@JsonPropertyOrder({"id", "categoryName", "displayOrder"})
public class ForumCategory extends BaseEntity {
    private Long id;
    private String categoryName;
    private Integer displayOrder;
}
