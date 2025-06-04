package com.qingqing.admin.controller;


import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.goods.GoodsDTO;
import com.qingqing.common.dto.admin.goods.GoodsPageDTO;
import com.qingqing.common.query.admin.GoodsPageQuery;
import com.qingqing.common.vo.JsonVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 二手商品表 前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@RestController
@RequestMapping("/admin/goods")
@Api(tags = "二手商品管理")
public class GoodsController {
    /**
     * 获取二手商品列表（条件+分页）
     * @param goodsPageQuery 分页查询条件
     * @return JsonVO<PageDTO<GoodsPageDTO>> 分页查询结果
     */
    @ApiOperation("获取二手商品列表（条件+分页）")
    @GetMapping("/query-all")
    public JsonVO<PageDTO<GoodsPageDTO>> queryAll(GoodsPageQuery goodsPageQuery){
        return JsonVO.success(null);
    }
    /**
     * 获取单个二手商品详细
     */
    @ApiOperation("获取单个二手商品详细")
    @GetMapping("/query/{id}")
    public JsonVO<GoodsDTO> query(@PathVariable("id") Long id){
        return JsonVO.success(null);
    }

    /**
     * 下架或审核二手商品
     */
    @ApiOperation("下架或审核二手商品")
    @PutMapping("/update-status")
    public JsonVO<String> updateStatus(){
        return JsonVO.success(null);
    }
}
