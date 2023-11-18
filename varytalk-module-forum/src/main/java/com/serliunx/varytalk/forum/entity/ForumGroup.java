package com.serliunx.varytalk.forum.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.serliunx.varytalk.common.base.BaseEntity;
import com.serliunx.varytalk.common.validation.forum.ForumGroupAddGroup;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 论坛群组实体
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
@JsonPropertyOrder({"id", "ownerId", "ownerName", "groupScore", "groupTag", "groupName"})
public class ForumGroup extends BaseEntity {

    /**
     * 群组唯一id
     */
    private Long id;

    /**
     * 群主id
     */
    private Long ownerId;

    /**
     * 群主名称
     */
    private String ownerName;

    /**
     * 群组积分(用于排名)
     */
    private Long groupScore;

    /**
     * 群组标签(唯一性)
     */
    @NotNull(message = "群组标签不能为空!", groups = ForumGroupAddGroup.class)
    @Pattern(regexp = "^[0-9a-zA-Z_]+$", message = "群组标签格式不符合要求! 只允许下划线_、数字0~9及大小写字母",
            groups = ForumGroupAddGroup.class)
    @Size(min = 4, max = 25, message = "群组标签长度必须为4到25个字符", groups = ForumGroupAddGroup.class)
    private String groupTag;

    /**
     * 群组名(唯一)
     */
    @NotNull(message = "群组名称不能为空!", groups = ForumGroupAddGroup.class)
    @Size(min = 2, max = 16, message = "群组标签长度必须为2到16个字符", groups = ForumGroupAddGroup.class)
    private String groupName;
}