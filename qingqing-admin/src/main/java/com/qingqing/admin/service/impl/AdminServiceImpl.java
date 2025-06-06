package com.qingqing.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingqing.admin.mapper.AdminMapper;
import com.qingqing.admin.service.AdminService;
import com.qingqing.common.constants.MessageConstant;
import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.*;
import com.qingqing.common.entity.Admin;
import com.qingqing.common.exception.AccountLockedException;
import com.qingqing.common.exception.AccountNotFoundException;
import com.qingqing.common.exception.BaseException;
import com.qingqing.common.exception.PasswordErrorException;
import com.qingqing.common.query.admin.AdminQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    /**
     * 管理员登录
     * @param adminLoginDto
     * @return Admin
     */
    @Override
    public Admin login(AdminLoginDto adminLoginDto){
        String username = adminLoginDto.getUserName();
        String password = adminLoginDto.getPassword();

        Admin admin = adminMapper.selectOne(
                new QueryWrapper<Admin>().eq("username", username)
        );

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (admin == null) {
            //账号不存在, 被全局异常捕获器捕获
            throw new AccountNotFoundException("没有找到账号");
        }

        //密码比对
        // 密码进行MD5处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(admin.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        //0 正常 1禁用
        if (admin.getStatus() == 1) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }
        //3、返回实体对象
        return admin;
    }

    /**
     * 更新管理员密码
     * @param updatePWDTO
     */
    @Override
    public void updatePassword(UpdatePWDTO updatePWDTO) {
        Long adminId = updatePWDTO.getId();
        String oldPassword = updatePWDTO.getOldPassword();
        String newPassword = updatePWDTO.getNewPassword();
        // 1. 根据管理员ID查询管理员信息
        Admin admin = adminMapper.selectById(adminId);
        if (admin == null) {
            // 管理员不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        oldPassword = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        // 2. 验证旧密码是否正确
        if (!oldPassword.equals(admin.getPassword())) {
            // 旧密码错误
            throw new PasswordErrorException("密码错误");
        }
        // 3. 更新密码
        newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        admin.setPassword(newPassword);
        // 4. 执行更新操作
        int rows = adminMapper.updateById(admin);
        if (rows == 0){
            // 更新失败，可能是因为管理员不存在或其他原因
            throw new AccountNotFoundException("未知原因，密码更新失败");
        }
    }

    /**
     * 查询管理员列表（条件+分页）
     * @param adminQuery
     * @return
     */
    @Override
    public PageDTO<AdminDTO> queryAll(AdminQuery adminQuery) {
        Page<Admin> page = new Page<>(adminQuery.getPageIndex(), adminQuery.getPageSize());
        // 创建查询条件
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        if (adminQuery.getUserName() != null) {
            queryWrapper.like("username", adminQuery.getUserName());
        }
        if (adminQuery.getStatus() != null) {
            queryWrapper.eq("status", adminQuery.getStatus());
        }

        // 1. 执行 MyBatis-Plus 分页查询
        Page<Admin> resultPage = adminMapper.selectPage(page, queryWrapper);
        if(resultPage.getTotal() == 0){
            throw new BaseException("没有查询到数据");
        }
        // 2. 将查询结果 Page<Admin> 转换为 PageDTO<AdminDTO>
        PageDTO<AdminDTO> pageDTO = new PageDTO<>();
        pageDTO.setPageIndex(resultPage.getCurrent());
        pageDTO.setPageSize(resultPage.getSize());
        pageDTO.setTotal(resultPage.getTotal());
        pageDTO.setPages(resultPage.getPages());

        // 3. 使用 Stream 和 BeanUtils.copyProperties 将 List<Admin> 转换为 List<AdminDTO>
        List<AdminDTO> adminDTOList = resultPage.getRecords().stream()
                .map(admin -> {
                    AdminDTO adminDTO = new AdminDTO();
                    // 将 admin 对象的属性复制到 adminDTO 中
                    BeanUtils.copyProperties(admin, adminDTO);
                    // 手动将密码字段设置为 ******
                    adminDTO.setPassword("******");
                    return adminDTO;
                })
                .collect(Collectors.toList());
        pageDTO.setRows(adminDTOList);
        return pageDTO;
    }

    /**
     * 添加管理员
     * @param adminAddDTO
     * @return
     */
    @Override
    public Long addAdmin(AdminAddDTO adminAddDTO) {
        // 创建 Admin 实体对象
        Admin admin = new Admin();
        // 将 AdminAddDTO 的属性复制到 Admin 实体对象中
        BeanUtils.copyProperties(adminAddDTO, admin);
        // 设置默认状态为启用（0）
        admin.setStatus(0);
        // 对密码进行 MD5 加密
        String encryptedPassword = DigestUtils.md5DigestAsHex(admin.getPassword().getBytes());
        admin.setPassword(encryptedPassword);
        // 插入到数据库
        int rows = adminMapper.insert(admin);
        if (rows > 0) {
            return admin.getId(); // 返回新创建的管理员ID
        } else {
            throw new BaseException("添加管理员失败");
        }
    }

    /**
     * 更新管理员信息
     * @param adminUpdateDTO
     */
    @Override
    public void updateAdmin(AdminUpdateDTO adminUpdateDTO) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminUpdateDTO, admin);
        int rows = adminMapper.updateById(admin);
        if (rows == 0) {
            throw new BaseException("更新管理员信息失败");
        }
    }

    /**
     * 更新管理员状态
     * @param adminStatusDTO
     */
    @Override
    public void updateAdminStatus(AdminStatusDTO adminStatusDTO) {
        // 2. 构造更新实体（只包含需要更新的字段）
        Admin admin = new Admin();
        admin.setId(adminStatusDTO.getId()); // 设置主键，MyBatis-Plus 会根据主键进行更新
        admin.setStatus(adminStatusDTO.getStatus());

        // 3. 执行更新操作
        // 方式一：使用 updateById (推荐，如果只更新实体中的非空字段)
         int rowsAffected = adminMapper.updateById(admin);

//        // 方式二：使用 UpdateWrapper (更灵活，可以指定更新字段，或者添加额外条件)
//        UpdateWrapper<Admin> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq("id", adminStatusDTO.getId()); // 根据ID查找要更新的记录
//        updateWrapper.set("status", adminStatusDTO.getStatus()); // 设置要更新的字段及其值

//        int rowsAffected = adminMapper.update(null, updateWrapper); // 第一个参数为 null 表示不使用实体进行更新，只使用 wrapper

        // 4. 判断更新结果
        if (rowsAffected == 0) {
            throw new BaseException("管理员不存在或状态未发生变化"); // 或者更具体的业务异常
        }
    }
}
