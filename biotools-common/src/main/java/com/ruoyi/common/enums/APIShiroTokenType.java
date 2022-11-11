package com.ruoyi.common.enums;

/**
 * 用于判断接口验证来源
 * 
 * @author ruoyi
 */
public enum APIShiroTokenType {
    WECHAT_REST("WECHAT", "微信端调用"),
    WEB_REST("WEB", "web前端调用"),
    APP_REST("APP", "app调用");

    private final String code;
    private final String info;

    APIShiroTokenType(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
