package com.ruoyi.common.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.FastArrayList;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.BeanUtils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class BeanMapper {

    /**
     * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
     */
    private static DozerBeanMapper dozer = new DozerBeanMapper();

    /**
     * 基于Dozer转换对象的类型.
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    /**
     * 基于Dozer转换Collection中对象的类型.
     */
    public static <T> List<T> mapList(List<?> sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<>();

        if (sourceList == null) {
            return new ArrayList<>();
        }

        sourceList.forEach(source -> {
            T destinationObject = dozer.map(source, destinationClass);
            destinationList.add(destinationObject);
        });

        return destinationList;
    }

    public static <S, T> T map(S source, Class<T> destinationClass, BeanMappingHandler<S, T> handler) {
        return handler.map(source, destinationClass);
    }

    /**
     * 基于Dozer转换Collection中对象的类型.
     */
    public static <S, T> List<T> mapList(List<S> sourceList, Class<T> destinationClass, BeanMappingHandler<S, T> handler) {
        List<T> destinationList = new ArrayList<>();
        for (S sourceObject : sourceList) {
            T destinationObject = handler.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }


    public static <T> Page<T> mapPage(Page sourcePage, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<>();
        for (Object sourceObject : sourcePage.getRecords()) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }

        Page<T> pageData = new Page<>();
        BeanUtils.copyProperties(sourcePage, pageData, "records");
        pageData.setRecords(destinationList);
        return pageData;
    }

    public static <S, T> Page<T> mapPage(Page<S> sourcePage, Class<T> destinationClass,
                                         BeanMappingHandler<S, T> handler) {
        List<T> destinationList = new ArrayList<>();
        for (S sourceObject : sourcePage.getRecords()) {
            T destinationObject = handler.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        Page<T> pageData = new Page<>();
        BeanUtils.copyProperties(sourcePage, pageData, "records");
        pageData.setRecords(destinationList);

        return pageData;
    }

    /**
     * 基于Dozer将对象A的值拷贝到对象B中.
     */
    public static void copy(Object source, Object destinationObject) {
        dozer.map(source, destinationObject);
    }
}
