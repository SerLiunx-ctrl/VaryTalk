package github.serliunx.varytalk.common.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ServletUtils {

    private ServletUtils(){}

    public static ServletRequestAttributes getRequestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    /**
     * 获取当前线程的请求对象
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取当前请求的ip地址
     * @return ip地址
     */
    public static String getIp(){
        return getIp(getRequest());
    }

    public static String getRequestURI(){
        return getRequest().getRequestURI();
    }

    public static String getAgent(){
        return getRequest().getHeader("user-agent");
    }

    public static String getHeader(String header){
        return getRequest().getHeader(header);
    }

    public static String getParameter(String parameter){
        return getRequest().getParameter(parameter);
    }

    /**
     * 获取特定请求的ip地址 (此方法源自ruoyi)
     * @param request 请求
     * @return ip地址
     */
    public static String getIp(HttpServletRequest request){
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
