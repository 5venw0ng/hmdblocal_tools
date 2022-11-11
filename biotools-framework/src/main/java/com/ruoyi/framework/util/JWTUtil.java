package com.ruoyi.framework.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.ruoyi.common.utils.DateUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class JWTUtil {
    // 过期时间 24 小时
    private static final long EXPIRE_TIME = 7 * 24 * 3600 * 1000L;
    private static final int EXPIRE_DAYS = 7;
    private static final String SEC = "adfontes";


    public static boolean verify(IdCodeType codeType,String identifier,String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SEC);
            Verification verifier = JWT.require(algorithm);

            switch (codeType){
                case OPENID:
                    verifier.withClaim("openId",identifier);
                    break;
                case USERNAME:
                    verifier.withClaim("userName",identifier);
                    break;
            }
            JWTVerifier jwtVerifier = verifier.build();
            DecodedJWT jwt = jwtVerifier.verify(token);

            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }



    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static String getOpenId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);

            return jwt.getClaim("openId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 签名
     * @return
     */
    public static String sign(IdCodeType idCodeType, String idCode){
        try {
            //Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Date date = DateUtils.addDays(new Date(),EXPIRE_DAYS);
            Algorithm algorithm = Algorithm.HMAC256(SEC);

            JWTCreator.Builder builder = JWT.create();
            switch (idCodeType){
                case OPENID:
                    builder = builder.withClaim("openId",idCode);
                    break;
                case USERNAME:
                    builder = builder.withClaim("userName",idCode);
                    break;
            }
            return builder.withExpiresAt(date).sign(algorithm);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static final String CLAIM_NAME = "username";

    public static String createToken(String username, String password) {
        return createToken(username, password, EXPIRE_TIME);
    }

    public static String createToken(String username, String password, long expireTime) {
        Date date = new Date(System.currentTimeMillis() + expireTime);
        // 加密处理密码
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(password);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new JWTVerificationException("token创建失败");
        }
        return JWT.create().withClaim(CLAIM_NAME, username).withExpiresAt(date).sign(algorithm);
    }

    public static void verify(String username, String dbPwd, String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(dbPwd);
            JWTVerifier jwtVerifier = JWT.require(algorithm).withClaim(CLAIM_NAME, username).build();
            jwtVerifier.verify(token);
        } catch (TokenExpiredException e) {
            throw new TokenExpiredException("token已过期");
        } catch (JWTVerificationException e) {
            throw new JWTVerificationException("token验证失败");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new JWTVerificationException("token创建失败");
        }
    }

    public static String getUserName(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(CLAIM_NAME).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 状态类型
     */
    public enum IdCodeType {

        OPENID,

        USERNAME;


    }
}
