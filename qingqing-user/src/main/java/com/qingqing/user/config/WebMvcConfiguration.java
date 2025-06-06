package com.qingqing.user.config; // 假设 WebMvcConfiguration 也属于用户模块

import com.qingqing.common.interceptor.JwtTokenInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 注意：这里移除了 @EnableSwagger2 和所有与 Docket 相关的方法和导入
@Configuration
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private JwtTokenInterceptor jwtTokenInterceptor;

    /**
     * 注册自定义拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/users/login", // 用户端登录路径
                        "/doc.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/swagger-ui.html",
                        "/csrf",
                        "/error",
                        "/favicon.ico",
                        "/imgs/**"
                        // 如果有其他不需要拦截的路径，例如图片上传路径的Web访问前缀，也需要添加
                        // "/imgs/**" // 示例：图片访问路径，通常不需要JWT拦截
                );
    }

}