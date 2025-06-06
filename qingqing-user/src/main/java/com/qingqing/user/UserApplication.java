package com.qingqing.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.qingqing.user.mapper")
@ComponentScan(basePackages = {"com.qingqing.user", "com.qingqing.common"}) // 添加 com.qingqing.common
@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
