package com.serliunx.varytalk.security.constant;

/**
 * 验证器所需的值类型
 * <li> 由 {@link com.serliunx.varytalk.security.annotation.ApiValidator} 声明.
 * <li> 由 {@link com.serliunx.varytalk.security.validator.Validator} 的实现类处理.
 * <li> 注解的内容保存在{@link com.serliunx.varytalk.security.support.ValidationContext} 验证上下文中,
 * 并会在系统按照顺序执行验证器时传入该 "验证上下文"
 * @author SerLiunx
 * @since 1.0
 */
public enum ValidationValueType {

    /**
     * 普通的值验证(全等)
     */
    NORMAL,

    /**
     * 普通的值验证(近似)
     * <li> 可用正则表达式实现同样效果, 功能有所冲突. 后续可能完全移除
     * @see ValidationValueType#REGEX
     */
    @Deprecated(since = "1.0")
    LIKE,

    /**
     * Spring表达式语言
     */
    SPEL,

    /**
     * 正则表达式
     */
    REGEX,
    ;
}
