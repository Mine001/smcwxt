package com.hulqframe.config.shiro.jwt;

import io.jsonwebtoken.*;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JwtUtil {
    // jti：jwt的唯一身份标识
    public static final String JWT_ID = UUID.randomUUID().toString();


    // 加密密文，私钥
    public static final String JWT_SECRET = "hulqFrame";
    public static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    public static Key signingKey = new SecretKeySpec(Base64.decodeBase64(JWT_SECRET), signatureAlgorithm.getJcaName());

    // 签发者
    public static final String  IS_SUER="system_admin";

    // 过期时间，单位毫秒
//    public static final int EXPIRE_TIME = 60 * 60 * 1000; // 一个小时
	public static final long EXPIRE_TIME = 7 * 24 * 3600 * 1000; // 一个星期


    // 创建jwt
    public static String createJWT(String audience, String account, String apis) throws Exception {
        // 设置头部信息
//		Map<String, Object> header = new HashMap<String, Object>();
//		header.put("typ", "JWT");
//		header.put("alg", "HS256");
        // 或
        // 指定header那部分签名的时候使用的签名算法，jjwt已经将这部分内容封装好了，只有{"alg":"HS256"}
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证的方式）
        Map<String, Object> claims = new HashMap<>();
        claims.put("apis", apis);
        // jti用户id，例如：20da39f8-b74e-4a9b-9a0f-a39f1f73fe64
        String jwtId = JWT_ID;
        // 生成JWT的时间
        long nowTime = System.currentTimeMillis();
        Date issuedAt = new Date(nowTime);
        // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露，是你服务端的私钥，在任何场景都不应该流露出去，一旦客户端得知这个secret，那就意味着客户端是可以自我签发jwt的
        // 为payload添加各种标准声明和私有声明
        JwtBuilder builder = Jwts.builder() // 表示new一个JwtBuilder，设置jwt的body
//				.setHeader(header) // 设置头部信息
                .setClaims(claims) // 如果有私有声明，一定要先设置自己创建的这个私有声明，这是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明
                .setId(jwtId) // jti(JWT ID)：jwt的唯一身份标识，根据业务需要，可以设置为一个不重复的值，主要用来作为一次性token，从而回避重放攻击
                .setIssuedAt(issuedAt) // iat(issuedAt)：jwt的签发时间
                .setIssuer(IS_SUER) // iss(issuer)：jwt签发者
                .setSubject(account) // sub(subject)：jwt所面向的用户，放登录的用户名，一个json格式的字符串，可存放userid，roldid之类，作为用户的唯一标志
                .signWith(signatureAlgorithm, signingKey ); // 设置签名，使用的是签名算法和签名使用的秘钥
        // 设置过期时间
        long expTime = EXPIRE_TIME;
        if (expTime >= 0) {
            long exp = nowTime + expTime;
            builder.setExpiration(new Date(exp));
        }
        // 设置jwt接收者
        if (audience == null || "".equals(audience)) {
            builder.setAudience("Tom");
        } else {
            builder.setAudience(audience);
        }
        return builder.compact();
    }

    // 解密jwt
    public static Claims parseJWT(String jwt)  {
        try{
            // 签名秘钥，和生成的签名的秘钥一模一样
            Claims claims = Jwts.parser() // 得到DefaultJwtParser
                    .setSigningKey(signingKey ) // 设置签名的秘钥
                    .parseClaimsJws(jwt).getBody(); // 设置需要解析的jwt
            return claims;
        }catch (ExpiredJwtException e){
            return null;
        }

    }
    // 解密jwt
    public static String getUserName(String token) {
        Claims claims=parseJWT(token);
        if(claims==null){
            return null;
        }
        return claims.getSubject();
    }
    // 解密jwt
    public static String getUserApis(String token) {
        Claims claims=parseJWT(token);
        if(claims==null){
            return null;
        }
        return (String) claims.get("apis");
    }
    /**
     * token是否过期
     * @return  true：过期
     */
    public static boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
    /**
     * token是否有效
     * */
    public static boolean isValid(String token) throws Exception {
        Claims claims=parseJWT(token);
        if(claims==null){
            return false;
        }
        return isTokenExpired(claims.getExpiration());

    }
}