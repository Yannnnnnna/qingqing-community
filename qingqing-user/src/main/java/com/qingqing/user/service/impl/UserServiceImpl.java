package com.qingqing.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingqing.common.constants.MessageConstant;
import com.qingqing.common.dto.user.UserChangePasswordDTO;
import com.qingqing.common.dto.user.UserLoginDTO;
import com.qingqing.common.dto.user.UserProfileUpdateDTO;
import com.qingqing.common.dto.user.UserRegisterDTO;
import com.qingqing.common.entity.User;
import com.qingqing.common.exception.AccountLockedException;
import com.qingqing.common.exception.AccountNotFoundException;
import com.qingqing.common.exception.BaseException;
import com.qingqing.common.exception.PasswordErrorException;
import com.qingqing.common.vo.user.UserProfileVO;
import com.qingqing.user.mapper.UserMapper;
import com.qingqing.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.UUID;


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
    // 从 application.yml 获取配置的统一基础路径和Web访问前缀
    @Value("${app.upload.base-path}")
    private String uploadBasePath;

    @Value("${app.upload.web-prefix}")
    private String webPrefix;

    /**
     * 上传用户头像并更新数据库
     * @param file 用户上传的头像文件
     * @param userId 当前用户的ID (你需要从认证信息中获取)
     * @return 上传成功后的头像URL
     */
    public String uploadUserAvatar(MultipartFile file, Long userId) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }

        // 拼接具体子目录，例如 /avatars/
        String subDirectory = "avatars/"; // 可以在这里定义子目录，保持文件结构清晰
        File targetDir = new File(uploadBasePath, subDirectory);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFileName = UUID.randomUUID().toString() + fileExtension;

        // 完整的文件本地路径
        Path filePath = Paths.get(targetDir.getAbsolutePath(), newFileName);

        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 生成前端可访问的URL
        // URL结构: webPrefix + subDirectory + newFileName
        String avatarUrl = webPrefix + subDirectory + newFileName;

        User user = this.getById(userId);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        user.setAvatar(avatarUrl);
        this.updateById(user);

        // 可以在这里添加日志
        return avatarUrl;
    }
    /**
     * 更新用户信息
     * @param dto
     */
    @Override
    public void updateProfile(UserProfileUpdateDTO dto) {
        Long userId = dto.getId();
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new AccountNotFoundException("用户不存在");
        }
        String openid =dto.getOpenid();
        user = userMapper.selectOne(
                new QueryWrapper<User>().eq("openid", openid)
        );
        if (user != null && !user.getId().equals(userId)) {
            // OpenID 已被其他用户使用
            throw new BaseException("OpenID 已被其他用户使用");
        }
        String phone =dto.getPhone();
        user = userMapper.selectOne(
                new QueryWrapper<User>().eq("phone", phone)
        );
        if (user != null && !user.getId().equals(userId)) {
            // 手机号已被其他用户使用
            throw new BaseException("手机号已被其他用户使用");
        }
        // 更新用户信息
        User  user1 = new User();
        BeanUtils.copyProperties(dto, user1);
        int rows = userMapper.updateById(user1);
        if (rows == 0) {
            // 更新失败，可能是因为用户不存在或其他原因
            throw new AccountNotFoundException("用户信息更新失败");
        }
    }


    /**
     * 获取用户详细
     * @param id
     * @return
     */
    @Override
    public UserProfileVO queryById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            return null; // 用户不存在
        }
        UserProfileVO userProfileVO = new UserProfileVO();
        BeanUtils.copyProperties(user, userProfileVO);
        userProfileVO.setPassword("******");
        return userProfileVO;

    }



    /**
     * 用户登录
     * @param adminLoginDto
     * @return
     */
    @Override
    public User login(UserLoginDTO adminLoginDto) {
        String phoneNumber = adminLoginDto.getPhone();
        String password = adminLoginDto.getPassword();

        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("phone", phoneNumber)
        );

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if ( user == null) {
            //账号不存在, 被全局异常捕获器捕获
            throw new AccountNotFoundException("登录失败，用户不存在");
        }

        //密码比对
        // 密码进行MD5处理
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        //0 正常 1禁用
        if (user.getStatus() == 1) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }
        //3、返回实体对象
        return user;
    }

    /**
     * 修改密码
     * @param dto
     */
    @Override
    public void updatePassword(UserChangePasswordDTO dto) {
        Long userId = dto.getId();
        String oldPassword = dto.getOldPassword();
        String newPassword = dto.getNewPassword();
        // 1. 根据管理员ID查询管理员信息
        User user = userMapper.selectById(userId);
        if (user == null) {
            // 用户
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        oldPassword = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        // 2. 验证旧密码是否正确
        if (!oldPassword.equals(user.getPassword())) {
            // 旧密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        // 3. 更新密码
        newPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        user.setPassword(newPassword);
        // 4. 执行更新操作
        int rows = userMapper.updateById(user);
        if (rows == 0){
            // 更新失败，可能是因为管理员不存在或其他原因
            throw new AccountNotFoundException("密码更新失败");
        }
    }

    /**
     * 用户注册
     * @param dto
     */
    @Override
    public Long register(UserRegisterDTO dto) {
        User user = new User();
        // 1. 检查手机号是否已存在
        User existingUser = userMapper.selectOne(
                new QueryWrapper<User>().eq("phone", dto.getPhone())
        );
        if (existingUser != null) {
            // 手机号已存在，抛出异常
            throw new BaseException("手机号已被注册");
        }
        // 2. 设置用户信息
        BeanUtils.copyProperties(dto, user);
        // 密码进行MD5处理
        String password = DigestUtils.md5DigestAsHex(dto.getPassword().getBytes());
        user.setPassword(password);
        // 3. 设置默认状态为正常（0）
        user.setStatus(0);
        user.setOpenid(UUID.randomUUID().toString());; // 生成默认 OpenID
        // 4. 执行插入操作
        int rows = userMapper.insert(user);
        if (rows == 0) {
            // 插入失败，可能是因为数据库异常或其他原因
            throw new BaseException("用户注册失败");
        }
        return user.getId();
    }

}
