package com.ruoyi.common.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import com.ruoyi.common.utils.spring.SpringUtils;

import java.util.Map;

/**
 * 获取i18n资源文件
 *
 * @author ruoyi
 */
public class MessageUtils
{
    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return 获取国际化翻译值
     */
    public static String message(String code, Object... args)
    {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    /**
     * @author zhuwenchao
     * @date 2021-03-04
     * @param code 比如 REQUEST_TIMEOUT.CODE 和 REQUEST_TIMEOUT.MSG，只需要放入 REQUEST_TIMEOUT
     * @return
     */
    public static Map<String, String> getMessage(String code)
    {
        return Map.of("code", MessageUtils.message(code+".CODE"),"msg",MessageUtils.message(code+".MSG"));
    }
}
