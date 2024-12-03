package homework.web.util;

import homework.web.constant.JwtClaimsConstant;
import homework.web.property.AppProperty;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtUtils {


    @Resource(name = "appProperty")
    private AppProperty appProperty;

    private static SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;
    private static String SECRET_KEY;
    private static long TTL;
    private static String TOKEN_NAME;

    @PostConstruct
    public void init() {
        SECRET_KEY = appProperty.getJwt().getSecretKey();
        TTL = appProperty.getJwt().getTtl();
        TOKEN_NAME = appProperty.getJwt().getTokenName();
    }

    public static String createJWTByUserId(Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, userId);
        return createJWT(claims);
    }

    public static String createJWT(Map<String, Object> claims) {
        return createJWT(SECRET_KEY, TTL, claims);
    }

    /**
     * 生成jwt
     * 使用Hs256算法, 私匙使用固定秘钥
     *
     * @param secretKey jwt秘钥
     * @param ttlMillis jwt过期时间(毫秒)
     * @param claims    设置的信息
     * @return
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 指定签名的时候使用的签名算法，也就是header那部分
        SecureDigestAlgorithm<SecretKey, SecretKey> algorithm = Jwts.SIG.HS256;

        // 生成JWT的时间
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        // 设置jwt的body
        return Jwts.builder()
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .claims(claims)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(key, algorithm)
                // 设置过期时间
                .expiration(exp)
                .issuedAt(new Date()).compact();
    }


    /**
     * 解析token
     *
     * @param token token
     * @return Jws<Claims>
     */
    public static Jws<Claims> parseClaim(String secretKey, String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }


    /**
     * Token解密
     *
     * @param secretKey jwt秘钥 此秘钥一定要保留好在服务端, 不能暴露出去, 否则sign就可以被伪造, 如果对接多个客户端建议改造成多个
     * @param token     加密后的token
     * @return 获得解析后的结构体
     */
    public static Claims parseJWT(String secretKey, String token) {
        return parseClaim(secretKey, token).getPayload();
    }

    /**
     * Token解密
     *
     * @param token 加密后的token
     * @return 获得解析后的结构体
     */
    public static Claims parseJWT(String token) {
        return parseClaim(SECRET_KEY, token).getPayload();
    }



}

