package com.adfontes.common.services;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public interface IGenericService<T> extends IService<T> {

    public T findTopOne(Wrapper<T> queryWrapper);

    public T findTopOne(Wrapper<T> queryWrapper,boolean filterByDate);

    public List<T> list(Wrapper wrapper, boolean filterByDate);
}

