package com.ruoyi.common.enums;

/**
 * 用户会话
 *
 * @author ruoyi
 */
public enum ReturnStatusEnum
{
    RETURN_REQUESTED("已创建", ""),
    RETURN_ACCEPTED("已批准", "" ),
    //这个状态是中间过渡状态，是检查用户的物流信息来确定
    RETURN_NOT_RECEIVED("待收货", ""),
    RETURN_RECEIVED("已收货", ""),
    RETURN_COMPLETED("已完成", ""),
    RETURN_MAN_REFUND("手工退换", ""),
    RETURN_CANCELLED("已取消", ""),

    RETURN_REQUESTED_EC("退款中", "待客服审核中"),
    RETURN_ACCEPTED_EC("退款中", "待货品退回"),
    RETURN_NOT_RECEIVED_EC("退款中", "待仓库签收"),
    RETURN_RECEIVED_EC("退款中", "检查无误即刻退款"),
    RETURN_COMPLETED_EC("退款成功", "货款会在五个工作日内原路返回"),
    RETURN_MAN_REFUND_EC("退款成功", "货款会在五个工作日内原路返回"),
    RETURN_CANCELLED_EC("退款失败", "物品破损严重或缺失");

    // 在这里 info 可以认为给前端描述的退款状态
    // description 可以认为附带的一些其他附加信息
    private final String info;
    private final String description;

    private ReturnStatusEnum(String info, String description)
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
