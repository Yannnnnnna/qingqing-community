package com.qingqing.user.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

@Configuration
@Slf4j
@EnableSwagger2
public class UserSwaggerConfig {

    @Bean("userApiDocket")  // 给Bean一个唯一名称
    public Docket createUserApi() {
        log.info("创建用户端API文档配置...");

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户端")  // 明确分组名，与 application.yml 中的 group-name 对应
                .apiInfo(buildUserApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.qingqing.user.controller")) // 扫描用户端 Controller
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList(apiKey()))
                .securityContexts(Arrays.asList(securityContext()));
    }

    private ApiInfo buildUserApiInfo() {
        return new ApiInfoBuilder()
                .title("青青社区用户端API") // 文档标题
                .description("用户端接口文档")
                .version("1.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "token", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[0])))
                .build();
    }
}