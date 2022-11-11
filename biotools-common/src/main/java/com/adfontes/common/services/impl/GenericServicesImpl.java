package com.adfontes.common.services.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.ruoyi.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GenericServicesImpl<M extends BaseMapper<T>, T> extends ServiceImpl<BaseMapper<T>, T>{

    protected final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 找到结果集的第一个
     * @param queryWrapper
     * @return
     */
    public T findTopOne(Wrapper<T> queryWrapper){
        List<T> list = list(queryWrapper);
        if(list!=null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    /**
     * 找到结果集第一个 过滤有效日期
     * @param queryWrapper
     * @param filterByDate
     * @return
     */
    public T findTopOne(Wrapper<T> queryWrapper,boolean filterByDate){
        List<T> list = list(queryWrapper,filterByDate);
        if(list!=null && !list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    public List<T> list(Wrapper wrapper,boolean filterByDate){
        return list(((GenericQueryWrapper<T>) wrapper).filterByDate());
    }

    @Override
    protected String getSqlStatement(SqlMethod sqlMethod) {
        //return super.getSqlStatement(sqlMethod);
        Class<?> clazz = mapperClass ;
        if( StringUtils.isNotNull( this.baseMapper )
                && StringUtils.isNotNull( this.baseMapper.getClass() ) ){
            clazz = this.baseMapper.getClass().getInterfaces()[0] ;
        }
        return SqlHelper.getSqlStatement(clazz, sqlMethod);
    }
}


