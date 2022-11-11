package com.ruoyi.common.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodMap<K,V> extends HashMap<K,V> {

    public String getString(K key){
        String obj = null;
        try {
            obj = (String)super.get(key);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public BigDecimal getBigDecimal(K key){
        BigDecimal obj = null;
        try {
            obj = (BigDecimal)super.get(key);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public Long getLong(K key){
        Long obj = null;
        try {
            obj = (Long)super.get(key);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public <T> List<T> getList(K key){
        List<T> obj = null;
        try {
            obj = (List<T>) super.get(key);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public <T> GoodMap<String,Object> getMap(K key){
        Map<String,Object> obj = null;
        try {
            obj = (Map<String,Object>) super.get(key);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return GoodMap.toGoodMap(obj);
    }

    public static GoodMap toGoodMap(Map map){
        GoodMap gm = new GoodMap();
        gm.putAll(map);
        return gm;
    }
}
