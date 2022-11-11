package com.ruoyi.common.enums;

/**
 * 库存预定策略
 */
public enum InventoryReserveFunEnum
{


    INVRO_FIFO_EXP("先出快过期","过期时间的近先出"),
    INVRO_LIFO_EXP("后出快过期","过期时间远的先出"),
    INVRO_FIFO_REC("先进先出","按入库日期先进先出"),
    INVRO_LIFO_REC("后进先出","按入库日期后进先出");


    private final String info;
    private final String description;

    private InventoryReserveFunEnum(String info,String description)
    {
        this.info = info;
        this.description = description;
    }

    public String getInfo()
    {
        return info;
    }

    public String getDescription(){
        return description;
    }

    public static String getOrderByFieldByEnum(InventoryReserveFunEnum irfe){
        switch (irfe){
            case INVRO_FIFO_EXP:
                return "+expireDate";
            case INVRO_FIFO_REC:
                return "+datetimeReceived";
            case INVRO_LIFO_EXP:
                return "-expireDate";
            case INVRO_LIFO_REC:
                return "-datetimeReceived";
        }
        return null;
    }
}
