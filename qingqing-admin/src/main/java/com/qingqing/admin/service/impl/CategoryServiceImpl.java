package com.qingqing.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingqing.admin.mapper.CategoryMapper;
import com.qingqing.admin.service.CategoryService;
import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.category.CategoryAddDTO;
import com.qingqing.common.dto.admin.category.CategoryPageDTO;
import com.qingqing.common.dto.admin.category.CategoryUpdateDTO;
import com.qingqing.common.entity.Category;
import com.qingqing.common.query.admin.CategoryPageQuery;
import org.springframework.beans.BeanUtils; // Import BeanUtils for property copying
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * 获取商品类别列表（条件+分页）
     * @param query
     * @return
     */
    @Override
    public PageDTO<CategoryPageDTO> queryAll(CategoryPageQuery query) {
        // 1. 创建 Mybatis-Plus 的分页对象
        // query.getPageIndex() 对应 Mybatis-Plus 的 current
        // query.getPageSize() 对应 Mybatis-Plus 的 size
        Page<Category> page = new Page<>(query.getPageIndex(), query.getPageSize());

        // 2. 构建查询条件
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        // 模糊查询类别名称
        wrapper.like(query.getName() != null && !query.getName().isEmpty(), "name", query.getName());
        // 精确匹配状态
        wrapper.eq(query.getStatus() != null, "status", query.getStatus());

        // 3. 调用 Mapper 的分页查询方法
        Page<Category> categoryPage = categoryMapper.selectPage(page, wrapper);

        // 4. 将查询结果 Category 实体列表转换为 CategoryPageDTO 列表
        List<CategoryPageDTO> dtoList = categoryPage.getRecords().stream()
                .map(category -> {
                    CategoryPageDTO dto = new CategoryPageDTO();
                    // 使用 BeanUtils 拷贝同名属性，避免手动一个一个设置
                    // 确保 CategoryPageDTO 中有 Category 对应的字段
                    BeanUtils.copyProperties(category, dto);
                    return dto;
                })
                .collect(Collectors.toList());

        // 5. 将查询结果封装到 PageDTO 中
        PageDTO<CategoryPageDTO> resultPageDTO = new PageDTO<>();
        resultPageDTO.setRows(dtoList); // 设置转换后的数据列表
        resultPageDTO.setTotal(categoryPage.getTotal()); // 设置总记录数
        resultPageDTO.setPageIndex(categoryPage.getCurrent()); // 设置当前页码
        resultPageDTO.setPageSize(categoryPage.getSize()); // 设置每页记录数
        resultPageDTO.setPages(categoryPage.getPages()); // 设置总页数

        return resultPageDTO;
    }

    /**
     * 添加商品类别
     * @param dto
     * @return
     */
    @Override
    public Long saveCategory(CategoryAddDTO dto) {
        Category category = new Category();
        // 使用 BeanUtils 拷贝同名属性
        BeanUtils.copyProperties(dto, category);
        int rows = categoryMapper.insert(category);
        if (rows <= 0) {
            throw new RuntimeException("添加商品类别失败");
        }
        // 返回新插入记录的 ID
        return category.getId();

    }

    /**
     * 修改商品类别信息
     * @param dto
     * @return
     */
    @Override
    public boolean updateCategory(CategoryUpdateDTO dto) {
        Category category = new Category();
        // 使用 BeanUtils 拷贝同名属性
        BeanUtils.copyProperties(dto,category);
        // 执行更新操作
        int rows = categoryMapper.updateById(category);
        // 如果更新的行数大于0，说明更新成功
        return rows > 0;
    }
}