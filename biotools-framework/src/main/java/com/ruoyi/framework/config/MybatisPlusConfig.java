package com.ruoyi.framework.config;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@EnableTransactionManagement(proxyTargetClass = true)
@Configuration
//@MapperScan( "com.**.**.mapper*")
public class MybatisPlusConfig {

    /*
     * 分页插件，自动识别数据库类型
     */
    /*@Bean
    版本升级，没有了
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
*/
//     @Bean
//     public MetaObjectHandler metaObjectHandler() {
//         return new CustomMetaObjectHandler();
//     }

    /**
     * 新多租户插件配置,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false 避免缓存万一出现问题
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new HamburgerTenantHandler()));
        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        // 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }



    /**
     * 自动填充功能
     *
     * @return
     */
    @Bean
    public GlobalConfig globalConfig() {
        String selectStrategy = env.getProperty("mybatis-plus.global-config.db-config.select-strategy");
        String idType = env.getProperty("mybatis-plus.global-config.db-config.idType");
        String insertStrategy = env.getProperty("mybatis-plus.global-config.db-config.insert-strategy");

        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.valueOf(idType));
        dbConfig.setSelectStrategy(FieldStrategy.valueOf(selectStrategy.toUpperCase()));
        dbConfig.setInsertStrategy(FieldStrategy.valueOf(insertStrategy.toUpperCase()));
        //dbConfig.setUpdateStrategy(FieldStrategy.IGNORED);


        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setDbConfig(dbConfig);
        return globalConfig;
    }

    @Autowired
    private Environment env;

    @Autowired
    private MybatisPlusProperties properties;

    @Autowired(required = false)
    private Interceptor[] interceptors;


    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {

        String typeAliasesPackage = env.getProperty("mybatis-plus.typeAliasesPackage");
        String mapperLocations = env.getProperty("mybatis-plus.mapperLocations");
        String configLocation = env.getProperty("mybatis-plus.configLocation");
        //typeAliasesPackage = setTypeAliasesPackage(typeAliasesPackage);
        VFS.addImplClass(SpringBootVFS.class);

        //final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        //这是乐观锁插件
        sessionFactory.setPlugins(mybatisPlusInterceptor());
        //sessionFactory.setPlugins(optimisticLockerInterceptor());
        sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
        sessionFactory.setGlobalConfig(globalConfig()); //FIXME 这里需要注意一下，在MetaHandler中使用Component注解无效
        //注册一个自定义 Map 返回,用于驼峰命名发  ConfigurationCustomizer 用法失败了
        sessionFactory.getObject().getConfiguration().setObjectWrapperFactory(new MapWrapperFactory());


        return sessionFactory.getObject();
    }
}
