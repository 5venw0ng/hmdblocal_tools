package com.ruoyi.common.enums;

/**
 * 用户账户场景枚举
 */
public enum AccountTypeScenesEnum
{


    ASS_ORDER_PAID("订单支付"),
    ASS_ORDER_SHIP("订单发货"),
    ASS_RETURN_APPROVED("退货批准"),
    ASS_RETURN_RECEIVED("退货入库"),
    ASS_LOGIN("登陆");


    private final String info;

    private AccountTypeScenesEnum(String info)
    {
        this.info = info;
    }

    public String getInfo()
    {
        return info;
    }

}
