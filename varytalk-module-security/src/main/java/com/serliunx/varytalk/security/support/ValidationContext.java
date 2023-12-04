package com.serliunx.varytalk.security.support;

import com.serliunx.varytalk.security.constant.ValidationType;
import com.serliunx.varytalk.security.constant.ValidationValueType;
import com.serliunx.varytalk.security.validator.Validator;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 接口校验数据上下文
 * @author SerLiunx
 * @since 1.0
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class ValidationContext {

    /**
     * 当校验器类型为{@link ValidationType}时生效
     * <li> 通过指定 {@link ValidationValueType} 来选择匹配的方式
     * <li> 默认支持: 全值匹配、SPEL、正则表达式等..
     */
    private String value;

    /**
     * 校验器类型
     * @see ValidationType
     */
    private ValidationType type;

    /**
     * 当校验器类型为{@link ValidationType}时生效
     *
     * <li> 默认为{@link ValidationValueType#NORMAL} (全值匹配)
     * 等价于{@link String#equals(Object)}
     * @see ValidationValueType
     */
    private ValidationValueType valueType;

    /**
     * 手动指定校验器
     * <li> 在校验器为独立模式时生效
     */
    private Validator validator;
}
