package com.qingqing.admin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Admin模块Web配置类 - 配置静态资源访问
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.upload.base-path}")
    private String uploadBasePath;

    @Value("${app.upload.web-prefix}")
    private String webPrefix;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置图片等静态资源的访问路径
        // 将 /imgs/** 的请求映射到本地存储目录
        registry.addResourceHandler(webPrefix + "**")
                .addResourceLocations("file:" + uploadBasePath)
                .setCachePeriod(3600); // 设置缓存时间为1小时

        // 保留默认的静态资源配置
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        // 启动时打印配置信息，便于调试
        System.out.println("=== Admin模块静态资源配置信息 ===");
        System.out.println("URL访问路径: " + webPrefix + "**");
        System.out.println("本地文件路径: file:" + uploadBasePath);
        System.out.println("完整访问示例: http://localhost:8888" + webPrefix + "goods/example.jpg");
        System.out.println("==============================");
    }
}