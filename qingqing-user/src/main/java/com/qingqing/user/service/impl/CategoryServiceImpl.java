package com.qingqing.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.category.CategoryAddDTO;
import com.qingqing.common.dto.admin.category.CategoryPageDTO;
import com.qingqing.common.dto.admin.category.CategoryUpdateDTO;
import com.qingqing.common.entity.Category;
import com.qingqing.common.query.admin.CategoryPageQuery;
import com.qingqing.common.vo.user.CategoryVO;
import com.qingqing.user.mapper.CategoryMapper;
import com.qingqing.user.service.CategoryService;
import org.springframework.beans.BeanUtils;
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
     * 获取所有商品类别列表（不分页）
     * @return
     */
    @Override
    public List<String> getAllCategories() {
        // 构建查询Wrapper，只查询有效状态的类别 (假设有一个status字段，0表示有效)
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, 0); // 根据你的Category实体设计，添加状态条件

//        // 关键修改：使用 selectList 结合 select(Category::getName)
//        // 这样 Mybatis-Plus 只会查询 'name' 字段，并将其直接映射到 List<String>
//        List<String> categoryNames = this.baseMapper.selectList(wrapper.select(Category::getName))
//                .stream()
//                .map(Category::getName)
//                .collect(Collectors.toList());

        // 或者更简洁的写法 (推荐，如果只查询一个字段，Mybatis-Plus可以直接返回List<String>)
         List<String> categoryNames = this.listObjs(wrapper.select(Category::getName), Object::toString);
        // 但通常 stream().map() 更通用和清晰

        return categoryNames;
    }

}