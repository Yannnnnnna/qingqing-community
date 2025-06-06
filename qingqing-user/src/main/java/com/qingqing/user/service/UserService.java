package com.qingqing.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.users.UpdateUsersStatusDTO;
import com.qingqing.common.dto.admin.users.UsersDTO;
import com.qingqing.common.dto.admin.users.UsersPageDTO;
import com.qingqing.common.dto.user.UserChangePasswordDTO;
import com.qingqing.common.dto.user.UserLoginDTO;
import com.qingqing.common.dto.user.UserProfileUpdateDTO;
import com.qingqing.common.dto.user.UserRegisterDTO;
import com.qingqing.common.entity.User;
import com.qingqing.common.query.admin.UserPageQuery;
import com.qingqing.common.vo.user.UserProfileVO;
import org.springframework.web.multipart.MultipartFile;

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
     * 获取用户详细
     * @param id
     * @return
     */
    UserProfileVO queryById(Long id);



    /**
     * 用户登录
     * @param adminLoginDto
     * @return
     */
    User login(UserLoginDTO adminLoginDto);

    /**
     * 修改密码
     * @param dto
     */
    void updatePassword(UserChangePasswordDTO dto);

    /**
     * 用户注册
     * @param dto
     */
    Long register(UserRegisterDTO dto);

    /**
     * 上传头像
     * @param file
     * @param currentUserId
     * @return
     */
    String uploadUserAvatar(MultipartFile file, Long currentUserId);

    /**
     * 更新用户信息
     * @param dto
     */
    void updateProfile(UserProfileUpdateDTO dto);
}
