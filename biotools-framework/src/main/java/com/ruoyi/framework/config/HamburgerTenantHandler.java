package com.ruoyi.framework.config;

/**
 * @Description: [重新实现tenant的处理方式]
 * @Author: 老王
 * @Date: 2022/6/13 3:05 下午
 */
public class HamburgerTenantHandler {//implements TenantLineHandler {
    /*@Override
    public Expression getTenantId() {
        return new LongValue(APISessionContext.getTenantId());
    }

    @Override
    public String getTenantIdColumn() {
        return "tenant_id";
    }

    @Override
    public boolean ignoreTable(String tableName) {
        //这里增加判断，如果传递来的tenant_id是空的，就全部都返回忽略，这也是从另一个角度实现全局查询了
        //如果拿不到任何tenantId，说明是主系统、账号，防止bug，还需要多一些验证
        APISessionContext.setTenantId(1L);
        if(StringUtils.isEmpty(APISessionContext.getTenantId())){
            return true;
        }else{
            return !"product".equalsIgnoreCase(tableName);
        }
    }*/
}
