package com.qingqing.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.users.UpdateUsersStatusDTO;
import com.qingqing.common.dto.admin.users.UsersDTO;
import com.qingqing.common.dto.admin.users.UsersPageDTO;
import com.qingqing.common.entity.User;
import com.qingqing.common.query.admin.UserPageQuery;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
public interface UserService extends IService<User> {
    /**
     * 获取用户列表（条件+分页）
     * @param query
     * @return
     */
    PageDTO<UsersPageDTO> queryAll(UserPageQuery query);

    /**
     * 获取用户详细
     * @param id
     * @return
     */
    UsersDTO queryById(Long id);

    /**
     * 修改状态
     * @param dto
     */
    void updateStatus(UpdateUsersStatusDTO dto);
}
