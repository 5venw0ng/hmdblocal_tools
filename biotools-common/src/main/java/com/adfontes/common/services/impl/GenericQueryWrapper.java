package com.adfontes.common.services.impl;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.SharedString;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.baomidou.mybatisplus.core.enums.SqlKeyword;
import com.baomidou.mybatisplus.core.enums.SqlLike;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.Entity;
import com.ruoyi.common.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class GenericQueryWrapper<T> extends AbstractWrapper<T, String, GenericQueryWrapper<T>> {

    private SharedString sqlSelect;

    public GenericQueryWrapper() {
        this((T) null);
    }

    public GenericQueryWrapper(T entity) {
        this.sqlSelect = new SharedString();
        super.setEntity(entity);
        super.initNeed();

        nonEmptyPrimary4Select();

        //这里针对时间进行处理
        if (entity instanceof Entity) {
            this.generateDateCondition((Entity)entity);
        }
    }

    /**
     * @Description: [处理查询的时候主键为空字符串的问题]
     * @Author: 老王
     * @Date: 2021/8/19 4:59 下午
     */
    private void nonEmptyPrimary4Select(){
        try {
            if(getEntity() == null || !(getEntity() instanceof BaseEntity)){
                return;
            }
            Field[] fs = getEntity().getClass().getDeclaredFields();
            for(Field f:fs){
                if(f.isAnnotationPresent(TableId.class)){
                    f.setAccessible(true);
                    if(StringUtils.isEmpty(f.get(getEntity()))){
                        f.set(getEntity(),null);
                    }
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            //e.printStackTrace(); 如果有异常就忽略
        }
    }

    private GenericQueryWrapper(T entity, Class<T> entityClass, AtomicInteger paramNameSeq, Map<String, Object> paramNameValuePairs, MergeSegments mergeSegments, SharedString lastSql, SharedString sqlComment) {
        this.sqlSelect = new SharedString();
        super.setEntity(entity);
        //this.entityClass = entityClass;
        this.setEntityClass(entityClass);
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.lastSql = lastSql;
        this.sqlComment = sqlComment;
    }

    public GenericQueryWrapper<T> select(String... columns) {
        for (int i = 0; i < columns.length; i++) {
            columns[i] = StringUtils.toUnderlineCase(columns[i]);
        }
        if (ArrayUtils.isNotEmpty(columns)) {
            this.sqlSelect.setStringValue(String.join(StringPool.COMMA, columns));
        }
        return this.typedThis;
    }

    @Override
    public String getSqlSelect() {
        return sqlSelect.getStringValue();
    }

    @Override
    public GenericQueryWrapper<T> eq(String column, Object val) {
        return super.eq(StringUtils.toUnderlineCase(column),val);
    }


    @Override
    public GenericQueryWrapper<T> eq(boolean condition, String column, Object val) {
        return super.eq(condition, (StringUtils.toUnderlineCase(column)), val);
    }

    @Override
    public GenericQueryWrapper<T> ne(String column, Object val) {
        return super.ne(StringUtils.toUnderlineCase(column),val);
    }
    @Override
    public GenericQueryWrapper<T> ne(boolean cond,String column, Object val) {
        return super.ne(cond,StringUtils.toUnderlineCase(column),val);
    }

    @Override
    public GenericQueryWrapper<T> gt(String column, Object val) {
        return super.gt(StringUtils.toUnderlineCase(column),val);
    }
    @Override
    public GenericQueryWrapper<T> ge(String column, Object val) {
        return super.ge(StringUtils.toUnderlineCase(column),val);
    }
    @Override
    public GenericQueryWrapper<T> lt(String column, Object val) {
        return super.lt(StringUtils.toUnderlineCase(column),val);
    }
    @Override
    public GenericQueryWrapper<T> le(String column, Object val) {
        return super.le(StringUtils.toUnderlineCase(column),val);
    }

    @Override
    public GenericQueryWrapper<T> isNull(String column) {
        return super.isNull(StringUtils.toUnderlineCase(column));
    }

    @Override
    public GenericQueryWrapper<T> isNotNull(String column) {
        return super.isNotNull(StringUtils.toUnderlineCase(column));
    }

    @Override
    public GenericQueryWrapper<T> like(String column, Object val) {
        return super.like(StringUtils.toUnderlineCase(column),val);
    }

    @Override
    public GenericQueryWrapper<T> like(boolean condition, String column, Object val) {
        return super.like(condition, StringUtils.toUnderlineCase(column), val);
    }

    @Override
    public GenericQueryWrapper<T> likeLeft(String column, Object val) {
        return super.likeLeft(true, StringUtils.toUnderlineCase(column), val);
    }

    @Override
    public GenericQueryWrapper<T> likeLeft(boolean condition,String column, Object val) {
        return super.likeLeft(condition, StringUtils.toUnderlineCase(column), val);
    }

    @Override
    public GenericQueryWrapper<T> likeRight(String column, Object val) {
        return super.likeRight(true, StringUtils.toUnderlineCase(column), val);
    }

    /**
     * 按日期过滤
     * @return
     */
    public GenericQueryWrapper<T> filterByDate(){
        return lt("fromDate",new Date())
                .and(qw -> qw.gt("thruDate",new Date()).or(qw1->qw1.isNull("thruDate")));
    }

    @Override
    protected GenericQueryWrapper<T> instance() {
        return new GenericQueryWrapper(getEntity(), /*this.entityClass*/ this.getEntityClass(), this.paramNameSeq, this.paramNameValuePairs, new MergeSegments(), SharedString.emptyString(), SharedString.emptyString());
    }

    @Override
    public GenericQueryWrapper<T> orderByDesc(String column) {
        return super.orderByDesc(StringUtils.toUnderlineCase(column));
    }

    @Override
    public GenericQueryWrapper<T> orderByAsc(String column) {
        return super.orderByAsc(StringUtils.toUnderlineCase(column));
    }

    @Override
    public GenericQueryWrapper<T> in(String column, Collection<?> coll) {
        return super.in(StringUtils.toUnderlineCase(column), coll);
    }

    @Override
    public GenericQueryWrapper<T> in(String column, Object... values) {
        return super.in(StringUtils.toUnderlineCase(column), values);
    }

    @Override
    public GenericQueryWrapper<T> in(boolean condition, String column, Collection<?> coll) {
        return super.in(condition, StringUtils.toUnderlineCase(column), coll);
    }

    @Override
    public GenericQueryWrapper<T> between(boolean condition, String column, Object val1, Object val2) {
        return super.between(condition, StringUtils.toUnderlineCase(column), val1, val2);
    }

    @Override
    public GenericQueryWrapper<T> notIn(String column, Object... value) {
        return super.notIn(StringUtils.toUnderlineCase(column), value);
    }

    @Override
    public GenericQueryWrapper<T> likeRight(boolean condition, String column, Object val) {
        return super.likeValue(condition,SqlKeyword.LIKE, StringUtils.toUnderlineCase(column), val, SqlLike.RIGHT);
    }

    @Override
    public GenericQueryWrapper<T> groupBy(String column) {
        return super.groupBy(StringUtils.toUnderlineCase(column));
    }

    @Override
    public GenericQueryWrapper<T> ge(boolean condition, String column, Object val) {
        return super.ge(condition, StringUtils.toUnderlineCase(column), val);
    }

    @Override
    public GenericQueryWrapper<T> le(boolean condition, String column, Object val) {
        return super.le(condition, StringUtils.toUnderlineCase(column), val);
    }
    /**
     * 这个方法主要的用处是对界面的时间筛选做快速的条件处理 cool
     * @author zhuwenchao
     * @date 2021-04-26
     * @param baseEntity
     * @return
     */
    public void generateDateCondition(Entity baseEntity){
        Map<String, Object> params = baseEntity.getParams();
        params.forEach((k,v)->{
            if (StringUtils.isEmpty(v)) {
                return;
            }

            String entityField = null;
            SqlKeyword sqlKeyword = null;
            if (StringUtils.contains(k, "begin")) {
                entityField = StringUtils.substringAfter(k, "begin");
                sqlKeyword = SqlKeyword.GE;
                if (String.valueOf(v).length() == 10) {
                    v += " 00:00:00";
                }
            }
            if (StringUtils.contains(k, "end")) {
                entityField = StringUtils.substringAfter(k, "end");
                sqlKeyword = SqlKeyword.LE;
                if (String.valueOf(v).length() == 10) {
                    v += " 23:59:59";
                }
            }

            if (StringUtils.isNotEmpty(entityField) ) {
                entityField = entityField.substring(0, 1).toLowerCase() + entityField.substring(1);
                addCondition(true, StringUtils.toUnderlineCase(entityField), sqlKeyword, v);
            }
        });
    }
}
