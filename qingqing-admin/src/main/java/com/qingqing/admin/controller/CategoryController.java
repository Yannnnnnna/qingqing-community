package com.qingqing.admin.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qingqing.admin.service.CategoryService;
import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.category.CategoryAddDTO;
import com.qingqing.common.dto.admin.category.CategoryPageDTO;
import com.qingqing.common.dto.admin.category.CategoryUpdateDTO;
import com.qingqing.common.query.admin.CategoryPageQuery;
import com.qingqing.common.vo.JsonVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品类别表 前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@RestController
@RequestMapping("/admin/category")
@Api(tags = "商品类别管理")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    /**
     * 获取商品类别列表（条件+分页）
     * @param query
     * @return 分页查询结果
     */
    @GetMapping("/query-all")
    @ApiOperation("获取商品类别列表（条件+分页）")
    public JsonVO<PageDTO<CategoryPageDTO> > queryAll( CategoryPageQuery query) {
        PageDTO<CategoryPageDTO> page = categoryService.queryAll(query);
        if(page == null || page.getRows() == null || page.getRows().isEmpty()) {
            return JsonVO.fail("没有查询到商品类别");
        }
        return JsonVO.success(page, "商品类别列表查询成功");
    }

    /**
     * 添加商品类别
     * @param dto
     * @return 分类id
     */
    @PostMapping("/add")
    @ApiOperation("添加商品类别")
    public JsonVO<Long> add(@RequestBody CategoryAddDTO dto) {
        Long id = categoryService.saveCategory(dto);
        return JsonVO.success(id, "商品类别添加成功");
    }

    /**
     * 修改商品类别
     * @param dto
     * @return 提示信息
     */
    @PutMapping("/update")
    @ApiOperation("修改商品类别")
    public JsonVO<String> update(@RequestBody CategoryUpdateDTO dto) {
        boolean updated = categoryService.updateCategory(dto);
        if (updated) {
            return JsonVO.success("商品类别修改成功");
        } else {
            return JsonVO.fail("商品类别修改失败，可能是ID不存在或数据未变更");
        }
    }

    /**
     * 删除商品类别
     * @param id
     * @return 提示信息
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除商品类别")
    public JsonVO<String> delete(@PathVariable("id") Long id) {
        boolean result = categoryService.removeById(id);
        if (result) {
            return JsonVO.success("商品类别删除成功");
        } else {
            return JsonVO.fail("商品类别删除失败，可能是ID不存在");
        }
    }
}
