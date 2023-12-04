package com.serliunx.varytalk.security.validator;

/**
 * 优先级
 * <li> 实现该接口可以设置某些功能的执行顺序
 * <li> {@link Order#getOrder()} 返回值越小、越优先执行.
 * @author SerLiunx
 * @since 1.0
 */
public interface Order {

    /**
     * 优先级: 值越小、优先级越高(即优先执行.)
     * @return 优先级
     */
    int getOrder();
}
