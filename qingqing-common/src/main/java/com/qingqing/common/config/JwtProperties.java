package com.qingqing.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//用于将配置文件中以指定前缀开头的属性值映射到类中的字段。
@ConfigurationProperties(prefix = "qingqing.jwt")
@Data
public class JwtProperties {

//    @Value("${qingqing.jwt.admin-secret-key}")
    private String adminSecretKey;

//    @Value("${qingqing.jwt.admin-ttl}")
    private Long adminTtl;

    // 添加用户密钥和TTL
//    @Value("${qingqing.jwt.user-secret-key}")
    private String userSecretKey;

//    @Value("${qingqing.jwt.user-ttl}")
    private Long userTtl;

}
