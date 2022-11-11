package com.ruoyi.common.enums;

/**
 * 商品类型枚举
 */
public enum ProductTypeEnum
{


    FINISHED_GOOD("成品"),
    DIGITAL_GOOD("数字商品"),
    SERVICE("服务"),
    RAW_MATERIAL("原材料"),
    AGGREGATED("组合商品"),
    ACCESSORY("附件"),
    DYNAMIC_SHIP("动态发货商品");


    private final String info;

    private ProductTypeEnum(String info)
    {
        this.info = info;
    }

    public String getInfo()
    {
        return info;
    }

}
