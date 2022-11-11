package com.adfontes.common.exception;

/**
 * @author zhuwenchao
 * @date 2021-07-15 15:30
 */
public enum WmsExceptionCodeEnum {
    ORDER_CANCEL(20002, "取消出库单失败！");

    private Integer code;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    WmsExceptionCodeEnum(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
