package com.tian.my_qa.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.util.StringUtils;

import java.util.Date;

public class JwtUtil {

    // 过期时间： 24小时
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    // 加密密钥
    public static final String APP_SECRET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    /**
     * 生成 token 字符串
     * @param id
     * @param nickname
     * @return
     */
    public static String getJwtToken(int id, String nickname) throws Exception {
        String jwtToken = Jwts.builder()
                // 设置 token 头部分
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                // 设置 token 的主题 subject，自定义
                .setSubject("token-demo")
                // 设置 token 的创建时间
                .setIssuedAt(new Date())
                // 设置过期时间，于何时过期
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                // 设置 token 的有效载荷
                .claim("id", Integer.toString(id))
                .claim("nickname", nickname)
                // 设置签名，使用的加密算法以及密钥
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
        
        return jwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if (StringUtils.isEmpty(jwtToken)) {
            return false;
        }
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取会员id，根据用户 id 查询数据库获取用户基本信息
     * @return
     */
    public static String getMemberIdByJwtToken(String jwtToken) {
        if (jwtToken.isEmpty()) return "";
        Jws<Claims> claimsJws =
                Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        Date dd = claims.getExpiration();
        System.out.println(dd.toString() + "-------------------------------------------");
        return (String) claims.get("id");
    }
}
