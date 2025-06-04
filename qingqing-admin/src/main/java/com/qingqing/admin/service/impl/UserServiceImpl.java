package com.qingqing.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingqing.admin.mapper.UserMapper;
import com.qingqing.admin.service.UserService;
import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.AdminDTO;
import com.qingqing.common.dto.admin.users.UpdateUsersStatusDTO;
import com.qingqing.common.dto.admin.users.UsersDTO;
import com.qingqing.common.dto.admin.users.UsersPageDTO;
import com.qingqing.common.entity.Admin;
import com.qingqing.common.entity.User;
import com.qingqing.common.exception.BaseException;
import com.qingqing.common.query.admin.UserPageQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取用户列表（条件+分页）
     * @param query
     * @return
     */
    @Override
    public PageDTO<UsersPageDTO> queryAll(UserPageQuery query) {
        Page<User> page = new Page<>(query.getPageIndex(), query.getPageSize());
        // 创建查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (query.getNickname() != null) {
            queryWrapper.like("nickname", query.getNickname());
        }
        if (query.getStatus() != null) {
            queryWrapper.eq("status", query.getStatus());
        }
        if (query.getPhone() != null) {
            queryWrapper.like("phone", query.getPhone());
        }
        if (query.getOpenid() != null) {
            queryWrapper.like("openid", query.getOpenid());
        }

        // 1. 执行 MyBatis-Plus 分页查询
        Page<User> resultPage = userMapper.selectPage(page, queryWrapper);

        // 2. 将查询结果 Page<Admin> 转换为 PageDTO<AdminDTO>
        PageDTO<UsersPageDTO> pageDTO = new PageDTO<>();
        pageDTO.setPageIndex(resultPage.getCurrent());
        pageDTO.setPageSize(resultPage.getSize());
        pageDTO.setTotal(resultPage.getTotal());
        pageDTO.setPages(resultPage.getPages());

        // 3. 使用 Stream 和 BeanUtils.copyProperties 将 List<User> 转换为 List<UsersPageDTO>
        List<UsersPageDTO> usersPageDTOList = resultPage.getRecords().stream()
                .map(user -> {
                    UsersPageDTO usersPageDTO = new UsersPageDTO();
                    // 将 user 对象的属性复制到 usersPageDTO 中
                    BeanUtils.copyProperties(user, usersPageDTO);
                    return usersPageDTO;
                })
                .collect(Collectors.toList());
        pageDTO.setRows(usersPageDTOList);
        return pageDTO;

    }

    /**
     * 获取用户详细
     * @param id
     * @return
     */
    @Override
    public UsersDTO queryById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return null; // 用户不存在
        }
        UsersDTO usersDTO = new UsersDTO();
        BeanUtils.copyProperties(user, usersDTO);
        return usersDTO;
    }

    /**
     * 修改状态
     * @param dto
     */
    @Override
    public void updateStatus(UpdateUsersStatusDTO dto) {
        // 2. 构造更新实体（只包含需要更新的字段）
        User user = new User();
        user.setId(dto.getUserId()); // 设置主键，MyBatis-Plus 会根据主键进行更新
        user.setStatus(dto.getStatus());

        // 3. 执行更新操作
        int rowsAffected = userMapper.updateById(user);
        if (rowsAffected > 0) {
            return;
        }else {
            throw new BaseException("更新失败");
        }
    }
}
