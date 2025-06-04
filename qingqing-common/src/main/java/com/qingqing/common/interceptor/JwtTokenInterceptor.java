package com.qingqing.common.interceptor;

import com.qingqing.common.constants.JwtClaimsConstant;
import com.qingqing.common.constants.JwtProperties;
import com.qingqing.common.utils.BaseContext;
import com.qingqing.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT Token 拦截器
 * 用于解析JWT token并设置当前用户信息
 */
@Slf4j
@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头中的token
        String token = request.getHeader("token");

        // 如果没有token，直接放行（某些接口可能不需要登录）
        if (!StringUtils.hasText(token)) {
            return true;
        }

        // 处理 Bearer token 格式
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        try {
            // 解析token
            log.info("解析JWT token: {}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());

            // 设置当前用户ID到ThreadLocal
            BaseContext.setCurrentId(userId);
            log.info("当前用户ID设置成功: {}", userId);

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

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求完成后清理ThreadLocal，防止内存泄漏
        BaseContext.removeCurrentId();
    }
}