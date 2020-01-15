package com.ning.home_admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
@MapperScan({"com.ning.home_admin.mapper","com.ning.home_admin.sytem.mapper"})
@EnableCaching
public class NkwShoppingApplication  {

    public static void main(String[] args) {
        SpringApplication.run(NkwShoppingApplication.class, args);
    }

}
