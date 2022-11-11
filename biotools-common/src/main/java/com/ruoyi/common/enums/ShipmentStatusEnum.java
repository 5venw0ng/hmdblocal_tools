package com.ruoyi.common.enums;

/**
 * @author zhuwenchao
 * @date 2021-07-29 14:52
 */
public enum ShipmentStatusEnum {
    SHIPMENT_CREATED("创建", ""),
    SHIPMENT_APPROVED("批准", "" ),
    //这个状态是中间过渡状态，是检查用户的物流信息来确定
    SHIPMENT_PICKING("拣货中", ""),
    SHIPMENT_PICKED("分拣完成", ""),
    SHIPMENT_SHIPPED("已发货", ""),
    SHIPMENT_DELIVERED("已投递", ""),
    SHIPMENT_RECEIVED("已接收", ""),

    SHIPMENT_CREATED_EC("未发货", "请耐心等待发货"),
    SHIPMENT_APPROVED_EC("未发货", "请耐心等待发货"),
    SHIPMENT_PICKING_EC("未发货", "请耐心等待发货"),
    SHIPMENT_PICKED_EC("未发货", "请耐心等待发货"),
    SHIPMENT_SHIPPED_EC("待收货", "商品已出库"),
    SHIPMENT_DELIVERED_EC("已签收", "你的快件已签收"),
    SHIPMENT_RECEIVED_EC("已签收", "你的快件已签收");

    // 在这里 info 可以认为给前端描述的退款状态
    // description 可以认为附带的一些其他附加信息
    private final String info;
    private final String description;

    private ShipmentStatusEnum(String info, String description)
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
