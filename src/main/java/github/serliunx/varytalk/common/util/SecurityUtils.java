package github.serliunx.varytalk.common.util;

import org.springframework.util.DigestUtils;

import java.util.Map;

public class SecurityUtils {

    private static final ThreadLocal<Map<String, Long>> userId = new ThreadLocal<>();

    private SecurityUtils(){}

    public static String generateMD5Message(String source){
        return DigestUtils.md5DigestAsHex(source.getBytes());
    }

    public static void setUserId(Map<String, Long> map){
        userId.set(map);
    }

    public static Long getUserId(){
        return userId.get().get("userId");
    }

    public static void removeUserId(){
        userId.remove();
    }
}
