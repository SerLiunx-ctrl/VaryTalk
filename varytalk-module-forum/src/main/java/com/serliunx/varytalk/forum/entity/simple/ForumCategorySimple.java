package com.serliunx.varytalk.forum.entity.simple;

import com.serliunx.varytalk.forum.entity.ForumSection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分区实体(简易)
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
public class ForumCategorySimple {
    private Long id;
    private String categoryName;
    private Integer displayOrder;
    private List<ForumSection> forumSections;
}
