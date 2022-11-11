package com.ruoyi.common.utils;

import com.ruoyi.common.utils.spring.SpringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Set;

/**
 * Cache工具类
 *
 * @author ruoyi
 */
public class QueryCache implements org.apache.ibatis.cache.Cache {
    private static final String QUERY_CACHE = "query-cache";


    private String id;

    //是mybatis必须要求的，必写。此id是xml中的namespace的值
    public QueryCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("未获取到缓存实例id");
        }
        this.id = id;
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        value = CloneUtils.clone(value);
        CacheUtils.put(QUERY_CACHE, key.toString(), value);
    }

    @Override
    public Object getObject(Object key) {
        Object obj = CloneUtils.clone(CacheUtils.get(QUERY_CACHE, key.toString()));
        return obj;
    }

    @Override
    public Object removeObject(Object key) {
        CacheUtils.remove(QUERY_CACHE, key.toString());
        return null;
    }

    @Override
    public void clear() {
        CacheUtils.removeAll(QUERY_CACHE);
    }

    @Override
    public int getSize() {
        return 0;
    }
}
