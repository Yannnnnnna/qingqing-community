package com.qingqing.admin.service.impl;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingqing.admin.mapper.CategoryMapper;
import com.qingqing.admin.service.CategoryService;
import com.qingqing.common.entity.Category;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品类别表 服务实现类
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
