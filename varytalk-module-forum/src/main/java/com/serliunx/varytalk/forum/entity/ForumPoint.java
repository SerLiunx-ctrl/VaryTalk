package com.serliunx.varytalk.forum.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.serliunx.varytalk.common.base.BaseEntity;
import com.serliunx.varytalk.common.validation.forum.ForumPointInsertGroup;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 论坛积分实体类
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
@JsonPropertyOrder({"id", "pointTag", "pointName"})
public class ForumPoint extends BaseEntity {

    private Long id;

    @NotNull(message = "积分标签不能为空!", groups = ForumPointInsertGroup.class)
    @Size(min = 2, max = 25, message = "标签长度必须位于4~25之间", groups = ForumPointInsertGroup.class)
    @Pattern(regexp = "^[a-zA-Z_]+$", message = "标签名称只能是英文大小写字母和下划线!", groups = ForumPointInsertGroup.class)
    private String pointTag;

    @NotNull(message = "积分名称不能为空!", groups = ForumPointInsertGroup.class)
    @Size(min = 2, max = 25, message = "名称长度必须位于4~25之间", groups = ForumPointInsertGroup.class)
    private String pointName;

    private Integer isSystem;
}
