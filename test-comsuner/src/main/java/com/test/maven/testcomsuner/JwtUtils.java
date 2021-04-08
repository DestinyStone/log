package com.test.maven.testcomsuner;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 *  <dependency>
 *             <groupId>io.jsonwebtoken</groupId>
 *             <artifactId>jjwt</artifactId>
 *             <version>0.9.0</version>
 *  </dependency>
 */

/**
 * @author: Mr.Wang
 * @create: 2021-04-08 09:46
 * @description:
 **/

public class JwtUtils {
    private static final SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;
    private static final String secretKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9"; //进行数字签名的私钥，一定要保管好，不能和我一样写到博客中。。。。。
    private static final int expire = 11900;

    /**
     *生成JWT字符串
     */
    public static String createJWT(String id, Map<String, Object> map)  {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + expire);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(id)//设置jwt
                .setIssuedAt(currentDate)//设置jwt签发日期
                .setExpiration(expireDate) //设置jwt的过期时间
                .signWith(algorithm, secretKey);
        map.forEach((key, value) -> {
            jwtBuilder.claim(key, value);
        });

        return jwtBuilder.compact();
    }

    /**
     * 解析JWT字符串
     *
     * @param jwt
     * @return
     * @throws io.jsonwebtoken.ExpiredJwtException 超时异常
     * @throws io.jsonwebtoken.MalformedJwtException 解析错误
     */
    public static Claims parseJWT(String jwt) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}