package com.ruoyi.framework.shiro.token;


import com.ruoyi.common.enums.APIShiroTokenType;
import com.ruoyi.framework.util.JWTUtil;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 第三方授权登录凭证
 * 注意这里要实现AuthenticationToken，不能继承UsernamePasswordToken
 * 同时重写getPrincipal()和getCredentials()两个方法
 * @author wujiawei0926@yeah.net
 */
public class JWTToken implements AuthenticationToken {

    /**
     *  授权类型
     *  这里可以使用枚举
     */
    private APIShiroTokenType type;

    // 第三方登录后 JWT的的token
    private String jwtToken;
    private String loginName;

    public JWTToken(final APIShiroTokenType type, final String jwtToken) {
        this.type = type;
        this.jwtToken = JWTUtil.getUsername(jwtToken);
    }

    @Override
    public Object getPrincipal() {
        return this.getLoginName();
    }

    @Override
    public Object getCredentials() {
        //return this.getUser().getOpenid();
        return this.getJwtToken();
    }




    public APIShiroTokenType getType() {
        return type;
    }

    public void setType(APIShiroTokenType type) {
        this.type = type;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }


    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}