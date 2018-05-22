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
    public final static int expiresMicrosecond = (int)YmlUtils.getProperty("jwt.expiresMicrosecond");
    public static void main(String[] args) {
		String token = JwtUtils.createJWT("tcc", null, "superadmin", "all");
		System.out.println(token);
		Claims claims = parseJWT("123423443234");
		if(claims != null){
			System.out.println(claims.get("user_name"));
			System.out.println(DateUtils.format(claims.getExpiration()));
		}
	}
    public static boolean isAutoLogin(Claims claims){
    	if(claims != null){
    		return claims.get("autoLogin") == null ? false : claims.get("autoLogin").equals(true);
    	}
    	return false;
    }
    /**
     * 验证jwt是否过期
     * @param jsonWebToken
     * @return
     */
    public static boolean isExpirated(Claims claims){
    	if(claims != null){
    		return System.currentTimeMillis() > claims.getExpiration().getTime();
    	}
    	return true;
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
     * @param autoLogin
     * @param roles
     * @param privileges
     * @return
     */
    public static String createJWT(String userInfo, Boolean autoLogin, String roles, String privileges) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .claim("userInfo", userInfo)
                .claim("autoLogin", autoLogin)
                .claim("roles", roles)
                .claim("privileges", privileges)
                .signWith(signatureAlgorithm, signingKey);
        //添加Token过期时间
        if (expiresMicrosecond >= 0) {
        	System.out.println(DateUtils.format(now));
            long expMillis = nowMillis + expiresMicrosecond;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
            System.out.println(DateUtils.format(exp));
        }
        
        //生成JWT
        return builder.compact();
    }
    
}
