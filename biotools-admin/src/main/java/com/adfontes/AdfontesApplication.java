package com.adfontes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan(basePackages={"com.ruoyi","com.adfontes"})
@EnableScheduling
@EnableAspectJAutoProxy(exposeProxy = true,proxyTargetClass=true)
@EnableAsync
public class AdfontesApplication {
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        System.setProperty("druid.mysql.usePingMethod", "false");//关闭druid数据库的ping，
        SpringApplication.run(AdfontesApplication.class, args);

    }
}
