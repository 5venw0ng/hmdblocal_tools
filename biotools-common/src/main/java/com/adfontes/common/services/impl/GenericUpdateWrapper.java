package com.adfontes.common.services.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoyi.common.utils.StringUtils;

import java.util.Map;

/**
 * @author zhuwenchao
 * @date 2021-05-27 11:32
 */
public class GenericUpdateWrapper<T> extends UpdateWrapper<T> {

    @Override
    public UpdateWrapper<T> set(boolean condition, String column, Object val) {
        return super.set(condition, StringUtils.toUnderlineCase(column), val);
    }

    @Override
    public UpdateWrapper<T> set(String column, Object val) {
        if ("".equals(val)) {
            val = null;
        }
        return set(true, column, val);
    }

    public UpdateWrapper<T> setAll(Map<String, Object> obj) {
        obj.forEach(this::set);
        return typedThis;
    }
}
