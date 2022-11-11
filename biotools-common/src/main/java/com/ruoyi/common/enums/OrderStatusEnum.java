package com.ruoyi.common.enums;

/**
 * 用户会话
 *
 * @author ruoyi
 */
public enum OrderStatusEnum
{

    ORDER_CREATED("已创建", ""),
    ORDER_APPROVED("已批准", ""),
    ORDER_COMPLETED("已完成", ""),
    ORDER_CANCELLED("已取消", ""),
    ORDER_RECEIVED("已签收", ""),
    ORDER_CLOSED("已关闭", ""),

    ORDER_CREATED_EC("待付款", "支付完毕就可以发货啦"),
    ORDER_APPROVED_EC("待发货", "正在等待仓库发货"),
    ORDER_COMPLETED_EC("待收货", "您的快递已经在路上了"),
    //电商逻辑里面，ORDER_CANCELLED_EC 和 ORDER_CLOSED_EC 都叫做已关闭。避免状态太多
    ORDER_CANCELLED_EC("已关闭", "订单已关闭"),
    ORDER_RECEIVED_EC("已签收", "您的快件已被签收"),
    ORDER_CLOSED_EC("已关闭", "您的订单已经关闭交易");

    private final String info;

    private final String description;

    private OrderStatusEnum(String info, String description)
    {
        this.info = info;
        this.description = description;
    }

    public String getInfo()
    {
        return info;
    }

    public String getDescription() {
        return description;
    }
}
