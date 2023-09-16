package github.serliunx.varytalk.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import github.serliunx.varytalk.common.config.autoconfiguer.SystemAutoConfigurer;
import github.serliunx.varytalk.common.base.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    private final SystemAutoConfigurer systemAutoConfigurer;

    @Autowired
    public JwtUtils(SystemAutoConfigurer systemAutoConfigurer) {
        this.systemAutoConfigurer = systemAutoConfigurer;
    }

    /**
     * 根据登录用户获取token
     * @param loginUser 登录用户
     * @return token
     */
    public String getLoginToken(LoginUser loginUser){
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(loginUser.getId()));
        map.put("userName", loginUser.getUsername());
        return generateToken(map, systemAutoConfigurer.getTokenExpireHour());
    }

    /**
     * 根据负载生成token
     * @param map 负载
     * @param expHours 过期时间(以小时为单位)
     * @return token
     */
    public String generateToken(Map<String, String> map, Integer expHours) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR, expHours);
        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(systemAutoConfigurer.getTokenSecret()));
    }

    /**
     * 验证token的有效性(异常未直接处理)
     * @param token token
     * @return 有效返回真, 否则返回假.
     */
    public boolean verifyToken(String token){
        DecodedJWT verify = verifyToken0(token);
        return verify != null;
    }

    public Long getUserId(String token){
        DecodedJWT decodedJWT = verifyToken0(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        String id = claims.get("userId").toString().replace("\"", "");
        return Long.valueOf(id);
    }

    private DecodedJWT verifyToken0(String token){
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(systemAutoConfigurer.getTokenSecret())).build();
        return verifier.verify(token);
    }
}
