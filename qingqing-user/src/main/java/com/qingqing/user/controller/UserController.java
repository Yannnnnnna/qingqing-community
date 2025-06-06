package com.qingqing.user.controller;


import com.qingqing.common.constants.JwtClaimsConstant;
import com.qingqing.common.config.JwtProperties;
import com.qingqing.common.dto.user.UserChangePasswordDTO;
import com.qingqing.common.dto.user.UserLoginDTO;
import com.qingqing.common.dto.user.UserProfileUpdateDTO;
import com.qingqing.common.dto.user.UserRegisterDTO;
import com.qingqing.common.entity.User;
import com.qingqing.common.utils.BaseContext;
import com.qingqing.common.utils.JwtUtil;
import com.qingqing.common.vo.JsonVO;
import com.qingqing.common.vo.admin.AdminLoginVO;
import com.qingqing.common.vo.user.UserLoginVO;
import com.qingqing.common.vo.user.UserProfileVO;
import com.qingqing.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@Slf4j
@RestController
@RequestMapping("/users")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
    /**
     * 用户登录
     * @param userLoginDto
     * @return UserLoginVO
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public JsonVO<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDto) { // 建议把adminLoginDto改为userLoginDto，以保持语义一致
        log.info("用户登录：{}", userLoginDto);
        User user = userService.login(userLoginDto);

        // 登录成功后,生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        // 对于普通用户登录，使用 USER_ID
        claims.put(JwtClaimsConstant.USER_ID, user.getId());

        // ***关键修改***：使用用户相关的密钥和TTL来生成JWT
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(), // <--- 修改为 getUserSecretKey()
                jwtProperties.getUserTtl(),       // <--- 修改为 getUserTtl()
                claims);

        UserLoginVO loginVO = new UserLoginVO();
        loginVO.setId(user.getId());
        loginVO.setToken(token);
        return JsonVO.success(loginVO, "登录成功");
    }


    /**
     * 更新密码
     * @param dto
     * @return JsonVO<String>
     */
    @ApiOperation("修改密码")
    @PutMapping("/change-password")
    public JsonVO<String> updatePassword(@RequestBody UserChangePasswordDTO dto) {
        log.info("修改密码：{}", dto);
        // 调用服务层方法更新密码
        userService.updatePassword(dto);
        return JsonVO.success("密码修改成功");
    }
    /**
     * 用户注册
     */
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public JsonVO<Long> register(@RequestBody UserRegisterDTO dto) {
        log.info("用户注册：{}", dto);
        // 调用服务层方法进行注册
        Long id = userService.register(dto);
        return JsonVO.success(id, "注册成功");
    }
    /**
     * 退出登录
     * @return JsonVO<String>
     */
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public JsonVO<String> logout() {
        log.info("用户退出登录");
        // 这里可以添加任何需要在退出时执行的逻辑
        return JsonVO.success("退出登录成功");
    }


    /**
     * 获取用户详细
     */
    @GetMapping("/profile/get/{id}")
    @ApiOperation("获取用户详细")
    public JsonVO<UserProfileVO> query(@PathVariable("id") Long id) {
        UserProfileVO user =  userService.queryById(id);
        if (user == null) {
            return JsonVO.fail("用户不存在");
        }
        return JsonVO.success(user, "用户查询成功");
    }
    /**
    * 上传头像
     * @param file 用户上传的头像文件
     * @return 包含头像URL的JSON响应
     */
    @ApiOperation("上传头像")
    @PostMapping("/profile/avatar")
    public JsonVO<String> uploadAvatar(@RequestPart  MultipartFile file) {
        // 实际开发中，这里需要从安全上下文中获取当前用户ID
        Long currentUserId = BaseContext.getCurrentId();
        try {
            String avatarUrl = userService.uploadUserAvatar(file, currentUserId);
            return JsonVO.success(avatarUrl, "头像上传成功");
        } catch (IllegalArgumentException e) {
            log.warn("头像上传请求参数异常：{}", e.getMessage());
            return JsonVO.fail(e.getMessage());
        } catch (Exception e) {
            log.error("头像上传过程中发生未知错误：{}", e.getMessage(), e);
            return JsonVO.fail("头像上传发生未知错误");
        }
    }
    /**
     * 更改用户信息
     */
    @ApiOperation("更改用户信息")
    @PutMapping("/profile/update")
    public JsonVO<String> update(@RequestBody UserProfileUpdateDTO dto) {
        log.info("更新用户信息：{}", dto);
        userService.updateProfile(dto);
        return JsonVO.success("用户信息更新成功");
    }

}
