package com.serliunx.varytalk.system.filter;

import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author SerLiunx
 * @since 1.0
 */
@Component
public class DefaultFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
