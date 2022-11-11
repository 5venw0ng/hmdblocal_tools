package com.ruoyi.common.exception;

import com.ruoyi.common.core.domain.AjaxResult;

import java.util.Map;

/**
 * 业务异常
 *
 * @author ruoyi
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected int code = AjaxResult.Type.ERROR.value();
    protected final String message;

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(Object data) {
        Map<String, String> map = (Map<String, String>)data;
        this.code = Integer.valueOf(map.get("code"));
        this.message = map.get("msg");
    }

    public BusinessException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
