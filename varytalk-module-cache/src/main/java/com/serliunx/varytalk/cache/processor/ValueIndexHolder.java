package com.serliunx.varytalk.cache.processor;

import com.serliunx.varytalk.cache.annotation.TagValue;
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
