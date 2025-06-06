package com.qingqing.user.controller;


import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.category.CategoryAddDTO;
import com.qingqing.common.dto.admin.category.CategoryPageDTO;
import com.qingqing.common.dto.admin.category.CategoryUpdateDTO;
import com.qingqing.common.query.admin.CategoryPageQuery;
import com.qingqing.common.vo.JsonVO;
import com.qingqing.common.vo.user.CategoryVO;
import com.qingqing.user.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品类别表 前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@RestController
@RequestMapping("/user/category")
@Api(tags = "商品类别管理")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    /**
     * 获取所有商品类别列表 (不分页，供小程序等使用)
     * @return 所有商品类别列表
     */
    @GetMapping("/query-list") // 建议修改路径，避免与分页接口混淆
    @ApiOperation("获取所有商品类别列表（不分页）")
    public JsonVO<List<String>> getAllCategoryNames() { // 返回类型改为 List<String>
        List<String> categoryNames = categoryService.getAllCategories(); // 调用服务层的新方法

        if (categoryNames == null || categoryNames.isEmpty()) {
            return JsonVO.fail("没有查询到任何商品类别名称");
        }
        return JsonVO.success(categoryNames, "商品类别名称列表查询成功");
    }



}
