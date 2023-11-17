package com.serliunx.varytalk.forum.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.serliunx.varytalk.common.base.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * 话题(帖子)标签
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
@JsonPropertyOrder({"id", "tagName"})
public class ForumTag extends BaseEntity {

    private Long id;

    @NotNull(message = "标签名称不能为空!")
    @Length(min = 2, max = 20, message = "标签名称长度只能在2-20个字符之间!")
    private String tagName;
}
