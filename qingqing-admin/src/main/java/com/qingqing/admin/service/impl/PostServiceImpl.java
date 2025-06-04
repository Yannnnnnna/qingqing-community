package com.qingqing.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingqing.admin.mapper.PostMapper;
import com.qingqing.admin.service.PostService;
import com.qingqing.common.entity.Post;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资讯论坛表 服务实现类
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

}
