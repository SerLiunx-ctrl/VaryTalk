package com.serliunx.varytalk.forum.entity.simple;

import lombok.Getter;
import lombok.Setter;

/**
 * 话题、帖子的标签(简易实体)
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
public class ForumTagSimple {

    private Long id;

    private String tagName;
}
