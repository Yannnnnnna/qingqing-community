package com.qingqing.admin.service;

import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.*;
import com.qingqing.common.entity.Admin;
import com.qingqing.common.exception.BaseException;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingqing.common.query.admin.AdminQuery;


/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
public interface AdminService extends IService<Admin> {

    /**
     *  管理员登录
     * @param adminLoginDto
     * @return Admin
     */
    Admin login(AdminLoginDto adminLoginDto);

    /**
     * 更新管理员密码
     * @param updatePWDTO
     */
    void updatePassword(UpdatePWDTO updatePWDTO);

    /**
     * 查询所有管理员
     * @return
     */
    PageDTO<AdminDTO> queryAll(AdminQuery adminQuery);

    /**
     * 添加管理员
     * @param adminAddDTO
     * @return
     */
    Long addAdmin(AdminAddDTO adminAddDTO);

    /**
     * 更新管理员信息
     * @param adminUpdateDTO
     */
    void updateAdmin(AdminUpdateDTO adminUpdateDTO);

    /**
     * 更新管理员状态
     * @param adminStatusDTO
     */
    void updateAdminStatus(AdminStatusDTO adminStatusDTO);
}
