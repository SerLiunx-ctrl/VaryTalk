package com.serliunx.varytalk.framework.cache.support;

import com.serliunx.varytalk.framework.cache.annotation.TagValue;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ValueIndexHolder {
    private int index;
    private TagValue tagValue;
}
