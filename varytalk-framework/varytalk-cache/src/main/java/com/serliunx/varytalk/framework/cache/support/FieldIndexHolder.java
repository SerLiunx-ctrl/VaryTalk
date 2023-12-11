package com.serliunx.varytalk.framework.cache.support;

import com.serliunx.varytalk.framework.cache.annotation.TagValue;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.lang.reflect.Field;

@Getter
@Setter
@Accessors(chain = true)
public class FieldIndexHolder {
    private Field field;
    private int index;
    private TagValue tagValue;
}
