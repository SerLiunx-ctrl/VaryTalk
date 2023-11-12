package com.serliunx.varytalk.forum.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.serliunx.varytalk.cache.annotation.TagValue;
import com.serliunx.varytalk.common.base.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 论坛板块实体(与分区的关系是多对一, 即一个分区下有多个板块. 一个板块只能属于一个分区)
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
@JsonPropertyOrder({"id", "categoryId", "sectionName", "categoryName"})
public class ForumSection extends BaseEntity {

    /**
     * 板块id
     */
    private Long id;

    /**
     * 所属分区id
     */
    @NotNull(message = "分区id不能为空!")
    @TagValue("categoryId")
    private Long categoryId;

    /**
     * 所属分区名称
     */
    private String categoryName;

    /**
     * 板块名称
     */
    @NotNull(message = "板块名称不能为空!")
    @Length(min = 2, max = 20, message = "板块名称长度只能在2-20个字符之间!")
    private String sectionName;
}
