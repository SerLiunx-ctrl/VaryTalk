package github.serliunx.varytalk.common.interceptor;

import github.serliunx.varytalk.common.exception.ServiceException;
import github.serliunx.varytalk.common.util.JwtUtils;
import github.serliunx.varytalk.common.util.SecurityUtils;
import github.serliunx.varytalk.project.system.entity.SystemUser;
import github.serliunx.varytalk.project.system.service.SystemUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;
    private final SystemUserService systemUserService;

    public GlobalInterceptor(JwtUtils jwtUtils, SystemUserService systemUserService) {
        this.jwtUtils = jwtUtils;
        this.systemUserService = systemUserService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod handlerMethod)){
            return false;
        }
        String token = request.getHeader("Authorization");
        if(token == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new ServiceException("你还未登录!", 401);
        }
        boolean result = jwtUtils.verifyToken(token);
        if(!result){
            return false;
        }

        Long userId = jwtUtils.getUserId(token);
        SystemUser systemUser = systemUserService.selectUserById(userId);
        if(systemUser == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new ServiceException("token验证失败, 用户不存在!", 401);
        }

        Map<String, Long> map = new HashMap<>();
        map.put("userId", userId);
        SecurityUtils.setUserId(map);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        SecurityUtils.removeUserId();
    }
}
