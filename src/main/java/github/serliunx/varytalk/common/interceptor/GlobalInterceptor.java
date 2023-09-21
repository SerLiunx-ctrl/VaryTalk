package github.serliunx.varytalk.common.interceptor;

import github.serliunx.varytalk.common.base.LoginUser;
import github.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import github.serliunx.varytalk.common.exception.ServiceException;
import github.serliunx.varytalk.common.util.JwtUtils;
import github.serliunx.varytalk.common.util.RedisUtils;
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
import java.util.concurrent.TimeUnit;

@Component
public class GlobalInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;
    private final SystemUserService systemUserService;
    private final SystemAutoConfigurer systemAutoConfigurer;
    private final RedisUtils redisUtils;

    public GlobalInterceptor(JwtUtils jwtUtils,
                             SystemUserService systemUserService,
                             SystemAutoConfigurer systemAutoConfigurer,
                             RedisUtils redisUtils) {

        this.jwtUtils = jwtUtils;
        this.systemUserService = systemUserService;
        this.systemAutoConfigurer = systemAutoConfigurer;
        this.redisUtils = redisUtils;
    }

    @Override
    @SuppressWarnings("all")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if(!(handler instanceof HandlerMethod handlerMethod)){
            return false;
        }
        String token = request.getHeader(systemAutoConfigurer.getAuthHeader());
        if(token == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new ServiceException("你还未登录!", 401);
        }
        boolean result = jwtUtils.verifyToken(token);
        if(!result){
            return false;
        }

        Long userId = jwtUtils.getUserId(token);
        LoginUser loginUser = (LoginUser) redisUtils.get(systemAutoConfigurer.getRedisPrefix().getOnlineUsers() +
                jwtUtils.getUsername(token));
        if(loginUser == null || !loginUser.getToken().equals(token)){
            throw new ServiceException("token已失效, 请重新登录!", 401);
        }
        SystemUser cachedUser = (SystemUser)redisUtils.get(systemAutoConfigurer.getRedisPrefix().getUserCache()
                + loginUser.getUsername());
        SystemUser systemUser = cachedUser == null ? systemUserService.selectUserById(userId) : cachedUser;
        if(systemUser == null){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new ServiceException("token验证失败, 用户不存在!", 401);
        }
        //将用户信息放到redis缓存中
        redisUtils.put(systemAutoConfigurer.getRedisPrefix().getUserCache() + systemUser.getUsername(),
                systemUser, systemAutoConfigurer.getRedisTtl().getUserCache(), TimeUnit.HOURS);

        Map<String, Long> map = new HashMap<>();
        map.put("userId", userId);
        SecurityUtils.setUserId(map);

        return true;
    }

    @Override
    @SuppressWarnings("all")
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        SecurityUtils.removeUserId();
    }
}
