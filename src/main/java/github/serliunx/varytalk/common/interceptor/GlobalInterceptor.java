package github.serliunx.varytalk.common.interceptor;

import github.serliunx.varytalk.common.exception.ServiceException;
import github.serliunx.varytalk.common.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class GlobalInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    public GlobalInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod handlerMethod)){
            return false;
        }
        String token = request.getHeader("Authorization");
        if(token == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new ServiceException("你还未登录!",401);
        }
        return jwtUtils.verifyToken(token);
    }
}
