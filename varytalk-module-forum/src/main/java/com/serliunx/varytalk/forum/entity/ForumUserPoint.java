package com.serliunx.varytalk.forum.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.serliunx.varytalk.framework.core.entity.base.BaseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户-积分关系实体
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
@JsonPropertyOrder({"id", "userId", "pointId", "points"})
public class ForumUserPoint extends BaseEntity {

    private Long id;

    @NotNull(message = "用户id不能为空!")
    private Long userId;

    @NotNull(message = "积分id不能为空!")
    private Long pointId;

    @NotNull(message = "积分数量不能为空!")
    private Long points;
}
