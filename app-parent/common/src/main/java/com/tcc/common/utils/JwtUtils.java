package com.tcc.common.utils;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {

    private final static String base64Secret = (String)YmlUtils.getProperty("jwt.base64Secret");
    private final static int expiresSecond = (int)YmlUtils.getProperty("jwt.expiresSecond");
    public static void main(String[] args) {
		String token = JwtUtils.createJWT("tcc", "superadmin", "all");
		System.out.println(token);
		Claims claims = parseJWT(token);
		System.out.println(claims.get("user_name"));
		System.out.println(DateUtils.format(claims.getExpiration()));
	}
    /**
     * 解析JWT
     * @param jsonWebToken
     * @return
     */
    public static Claims parseJWT(String jsonWebToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Secret))
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (Exception ex) {
            return null;
        }
    }
    /**
     * 生成JWT
     * @param userInfo
     * @param roles
     * @param privileges
     * @return
     */
    public static String createJWT(String userInfo, String roles, String privileges) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("userInfo", userInfo)
                .claim("roles", roles)
                .claim("privileges", privileges)
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        if (expiresSecond >= 0) {
        	System.out.println(DateUtils.format(now));
            long expMillis = nowMillis + expiresSecond;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
            System.out.println(DateUtils.format(exp));
        }
        
        //生成JWT
        return builder.compact();
    }
}
