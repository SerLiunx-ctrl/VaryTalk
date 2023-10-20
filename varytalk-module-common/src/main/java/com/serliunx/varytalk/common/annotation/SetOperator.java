package com.serliunx.varytalk.common.annotation;

import com.serliunx.varytalk.common.constants.OperationType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据操作者定义
 * <p>
 * 默认操作类型为创建数据 {@link OperationType#CREATE}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SetOperator {

    /**
     * 需修改的参数类型, 如存在多个则默认操作第一个
     */
    Class<?> value();

    /**
     * 需要修改的属性名称, 默认 createBy
     */
    String fieldName() default "createBy";

    /**
     * 操作类型, 默认为创建
     *
     * <li> 暂时没有用处
     */
    OperationType operation() default OperationType.CREATE;
}
