package com.qingqing.admin.controller;


import com.qingqing.admin.service.AdminService;
import com.qingqing.common.constants.JwtClaimsConstant;
import com.qingqing.common.config.JwtProperties;
import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.*;
import com.qingqing.common.entity.Admin;
import com.qingqing.common.query.admin.AdminQuery;
import com.qingqing.common.utils.BaseContext;
import com.qingqing.common.utils.JwtUtil;
import com.qingqing.common.vo.JsonVO;
import com.qingqing.common.vo.admin.AdminLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 管理员表 前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@Slf4j
@RestController
@Api(tags = "管理员管理")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 管理员登录
     * @param adminLoginDto
     * @return AdminLoginVO
     */
    @ApiOperation("管理员登录")
    @PostMapping("/login")
    public JsonVO<AdminLoginVO> login(@RequestBody AdminLoginDto adminLoginDto) {
        log.info("员工登录：{}", adminLoginDto);
        Admin admin = adminService.login(adminLoginDto);

        // 登录成功后,生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        // 管理员使用ADMIN_ID
        claims.put(JwtClaimsConstant.ADMIN_ID, admin.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
        AdminLoginVO loginVO = new AdminLoginVO();
        loginVO.setId(admin.getId());
        loginVO.setToken(token);
        loginVO.setRole(admin.getRole());
        loginVO.setUserName(admin.getUsername());
        return JsonVO.success(loginVO, "登录成功");
    }

    /**
     * 更新密码
     * @param updatePWDTO
     * @return JsonVO<String>
     */
    @ApiOperation("修改密码")
    @PutMapping("/change-password")
    public JsonVO<String> updatePassword(@RequestBody UpdatePWDTO updatePWDTO) {
        log.info("修改密码：{}", updatePWDTO);
        // 调用服务层方法更新密码
        adminService.updatePassword(updatePWDTO);
        return JsonVO.success("密码修改成功");
    }

    /**
     * 退出登录
     * @return JsonVO<String>
     */
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public JsonVO<String> logout() {
        log.info("管理员退出登录");
        // 这里可以添加任何需要在退出时执行的逻辑
        return JsonVO.success("退出登录成功");
    }

    /**
     * 查询管理员列表（条件+分页）
     * @param adminQuery
     * @return JsonVO<PageDTO<AdminDTO>>
     */
    @ApiOperation("查询管理员列表（条件+分页）")
    @GetMapping("/query-all")
    public JsonVO<PageDTO<AdminDTO>> queryAdmin(AdminQuery adminQuery){
        PageDTO<AdminDTO> list = adminService.queryAll(adminQuery);
        return JsonVO.success(list, "管理员列表查询成功");
    }

    /**
     *  添加管理员
     * @param adminAddDTO
     * @return
     */
    @ApiOperation("添加管理员")
    @PostMapping("/add")
    public JsonVO<Long> addAdmin(@RequestBody AdminAddDTO adminAddDTO) {
        log.info("添加管理员：{}", adminAddDTO);
        // 调用服务层方法添加管理员
        Long adminId = adminService.addAdmin(adminAddDTO);
        return JsonVO.success(adminId, "管理员添加成功");

    }

     /**
     * 删除管理员
     * @param id
     * @return
     */
    @ApiOperation("删除管理员")
    @DeleteMapping("/delete/{id}")
    @SecurityRequirement(name = "Authorization")
    public JsonVO<String> deleteAdmin(@PathVariable Long id) {
        log.info("删除管理员，ID：{}", id);
        // 调用服务层方法删除管理员
        if (BaseContext.getCurrentId().equals(id)){
            return JsonVO.fail("不能删除当前登录的管理员");
        }
        adminService.removeById(id);
        return JsonVO.success("管理员删除成功");
    }

    /**
     * 更新管理员信息
     * @param adminUpdateDTO
     * @return JsonVO<String>
     */
    @PutMapping("/update")
    @ApiOperation("更新管理员信息")
   public JsonVO<String> updateAdmin(@RequestBody AdminUpdateDTO adminUpdateDTO){
        log.info("更新管理员信息:{}", adminUpdateDTO);
        // 调用服务层方法更新管理员信息
        adminService.updateAdmin(adminUpdateDTO);
        return JsonVO.success("管理员更新成功");
   }

    /**
     * 启用或禁用管理员
     *
     */
    @ApiOperation("启用或禁用管理员")
    @PutMapping("/update-status")
    public  JsonVO<String> updateAdminStatus( @Validated @RequestBody AdminStatusDTO adminStatusDTO) {
        log.info("更新管理员状态:{}", adminStatusDTO);
        // 参数校验
        adminService.updateAdminStatus(adminStatusDTO);
        return JsonVO.success("管理员状态更新成功");
    }
}
