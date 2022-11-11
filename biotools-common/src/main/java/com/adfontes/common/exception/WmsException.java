package com.adfontes.common.exception;

import com.ruoyi.common.exception.BusinessException;

/**
 * @author zhuwenchao
 * 针对仓库系统的异常
 * @date 2021-07-15 15:27
 */
public class WmsException extends BusinessException {
    public WmsException(String message) {
        super(message);
    }

    public WmsException(int code, String message) {
        super(code, message);
    }

    public WmsException(String message, Throwable e) {
        super(message, e);
    }
}
