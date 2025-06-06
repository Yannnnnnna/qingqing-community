package com.qingqing.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.qingqing.admin.mapper")
@ComponentScan(basePackages = {"com.qingqing.admin", "com.qingqing.common"}) // 添加 com.qingqing.common
@SpringBootApplication
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
