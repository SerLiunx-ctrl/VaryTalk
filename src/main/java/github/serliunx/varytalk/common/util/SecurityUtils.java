package github.serliunx.varytalk.common.util;

import org.springframework.util.DigestUtils;

public class SecurityUtils {

    private SecurityUtils(){}

    public static String generateMD5Message(String source){
        return DigestUtils.md5DigestAsHex(source.getBytes());
    }
}
