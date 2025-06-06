package com.qingqing.common.interceptor;

import com.qingqing.common.constants.JwtClaimsConstant;
import com.qingqing.common.config.JwtProperties;
import com.qingqing.common.utils.BaseContext;
import com.qingqing.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT Token 拦截器
 * 用于解析JWT token并设置当前用户信息
 * 支持管理员登录和普通用户登录
 */
@Slf4j
@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            // 当前拦截到的不是动态方法，直接放行
            return true;
        }

        // 1、从请求头中获取令牌（支持多种方式）
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            token = request.getHeader("Authorization");
            if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
        }

        // 如果没有token，直接放行（通过拦截器配置来控制哪些接口需要认证）
        if (!StringUtils.hasText(token)) {
            return true;
        }

        try {
            // 2、解析token
            log.info("解析JWT token: {}", token);

            Claims claims = null;
            String userType = null;
            boolean adminKeySucceeded = false;
            boolean userKeySucceeded = false;

            // 尝试用管理员密钥解析
            try {
                claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
                userType = "ADMIN";
                adminKeySucceeded = true;
                log.info("使用管理员密钥解析成功");
            } catch (Exception adminException) {
                log.info("管理员密钥解析失败，尝试用户密钥: {}", adminException.getMessage());
            }

            // 如果管理员密钥解析失败，尝试用户密钥
            if (!adminKeySucceeded) {
                try {
                    claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
                    userType = "USER";
                    userKeySucceeded = true;
                    log.info("使用用户密钥解析成功");
                } catch (Exception userException) {
                    log.error("所有密钥都解析失败");
                    throw new RuntimeException("token解析失败");
                }
            }

            // 如果两种密钥都未能成功解析，则抛出异常
            if (!adminKeySucceeded && !userKeySucceeded) {
                throw new RuntimeException("token解析失败"); // 这个分支理论上不会走到，因为上面的catch会抛出
            }


            // 3、获取用户ID
            // 在这里，我们将根据解析成功的userType来严格获取对应的ID
            Long userId = null;
            if ("ADMIN".equals(userType)) {
                Object adminIdObj = claims.get(JwtClaimsConstant.ADMIN_ID);
                if (adminIdObj == null) {
                    log.error("管理员token中未找到字段: {}", JwtClaimsConstant.ADMIN_ID);
                    throw new RuntimeException("管理员token中缺少管理员ID信息");
                }
                if (adminIdObj instanceof Number) {
                    userId = ((Number) adminIdObj).longValue();
                } else {
                    userId = Long.valueOf(adminIdObj.toString());
                }
                log.info("成功提取管理员ID: {}", userId);
            } else if ("USER".equals(userType)) {
                Object userIdObj = claims.get(JwtClaimsConstant.USER_ID);
                if (userIdObj == null) {
                    log.error("用户token中未找到字段: {}", JwtClaimsConstant.USER_ID);
                    throw new RuntimeException("用户token中缺少用户ID信息");
                }
                if (userIdObj instanceof Number) {
                    userId = ((Number) userIdObj).longValue();
                } else {
                    userId = Long.valueOf(userIdObj.toString());
                }
                log.info("成功提取用户ID: {}", userId);
            }

            if (userId == null) {
                throw new RuntimeException("token中缺少用户ID信息"); // 兜底检查，理论上上面已处理
            }

            // 4、设置当前用户ID到ThreadLocal
            BaseContext.setCurrentId(userId);

            log.info("当前{}ID设置成功: {}", userType.equals("ADMIN") ? "管理员" : "用户", userId);
            return true;

        } catch (Exception e) {
            log.error("JWT token解析失败: {}", e.getMessage());
            // token解析失败，返回401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"message\":\"token无效或已过期\"}");
            return false;
        }
    }

    // `extractUserId` 方法不再需要，其逻辑已内联到 `preHandle`
    // 如果你依然想保持一个独立的方法，可以将其调整为更纯粹的转换方法，
    // 但核心的判断逻辑在 preHandle 中直接处理更清晰。
    // private Long extractUserId(Claims claims, String userType) { ... }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求完成后清理ThreadLocal，防止内存泄漏
        BaseContext.removeCurrentId();
    }
}