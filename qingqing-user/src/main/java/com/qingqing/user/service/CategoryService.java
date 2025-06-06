package com.qingqing.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.category.CategoryAddDTO;
import com.qingqing.common.dto.admin.category.CategoryPageDTO;
import com.qingqing.common.dto.admin.category.CategoryUpdateDTO;
import com.qingqing.common.entity.Category;
import com.qingqing.common.query.admin.CategoryPageQuery;
import com.qingqing.common.vo.user.CategoryVO;

import java.util.List;

/**
 * <p>
 * 商品类别表 服务类
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
public interface CategoryService extends IService<Category> {


    /**
     * 查询商品类别列表
     * @return
     */
    // 修改为只返回类别名称列表
    List<String> getAllCategories(); // 返回类型改为 List<String> getAllCategories();
}
