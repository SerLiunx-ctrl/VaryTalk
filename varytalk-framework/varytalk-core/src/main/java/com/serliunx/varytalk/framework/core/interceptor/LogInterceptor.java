package com.serliunx.varytalk.framework.core.interceptor;

import com.serliunx.varytalk.framework.core.tool.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@SuppressWarnings("all")
public class LogInterceptor implements HandlerInterceptor {

    private final Logger logger;
    private final ThreadLocal<Long> timeStarted = new ThreadLocal<>();

    public LogInterceptor() {
        logger = LoggerFactory.getLogger(LogInterceptor.class);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String ip = ServletUtils.getIp();
        String requestURI = ServletUtils.getRequestURI();

        logger.debug("新的请求来自 -> {}, 请求URI -> {}", ip, requestURI);
        timeStarted.set(System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        String ip = ServletUtils.getIp();
        String requestURI = ServletUtils.getRequestURI();

        logger.debug("来自请求 -> {}, 请求URI -> {}, 已结束. 耗时 {}毫秒.", ip, requestURI,
                System.currentTimeMillis() - timeStarted.get());
        timeStarted.remove();
    }
}
