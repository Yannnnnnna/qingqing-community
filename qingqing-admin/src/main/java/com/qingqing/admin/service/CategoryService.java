package com.qingqing.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.category.CategoryAddDTO;
import com.qingqing.common.dto.admin.category.CategoryPageDTO;
import com.qingqing.common.dto.admin.category.CategoryUpdateDTO;
import com.qingqing.common.entity.Category;
import com.qingqing.common.query.admin.CategoryPageQuery;

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
     * 获取商品类别列表（条件+分页）
     * @param query
     * @return
     */
    PageDTO<CategoryPageDTO> queryAll(CategoryPageQuery query);

    /**
     * 添加商品类别
     * @param dto
     * @return
     */
    Long saveCategory(CategoryAddDTO dto);

    /**
     * 修改商品类别
     * @param dto
     * @return
     */
    boolean updateCategory(CategoryUpdateDTO dto);
}
