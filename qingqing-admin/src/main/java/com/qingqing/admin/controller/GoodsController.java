package com.qingqing.admin.controller;


import com.qingqing.admin.service.GoodsService;
import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.goods.GoodsDTO;
import com.qingqing.common.dto.admin.goods.GoodsPageDTO;
import com.qingqing.common.dto.admin.goods.UpdateGoodsStatusDTO;
import com.qingqing.common.query.admin.GoodsPageQuery;
import com.qingqing.common.vo.JsonVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
    @Autowired
    GoodsService goodsService;
    /**
     * 获取二手商品列表（条件+分页）
     * @param goodsPageQuery 分页查询条件
     * @return JsonVO<PageDTO<GoodsPageDTO>> 分页查询结果
     */
    @ApiOperation("获取二手商品列表（条件+分页）")
    @GetMapping("/query-all")
    public JsonVO<PageDTO<GoodsPageDTO>> queryAll(@Validated GoodsPageQuery goodsPageQuery){
        PageDTO<GoodsPageDTO> pageDTO = goodsService.queryAll(goodsPageQuery);
        return JsonVO.success(pageDTO, "商品列表查询成功");

    }
    /**
     * 获取单个二手商品详细
     */
    @ApiOperation("获取单个二手商品详细")
    @GetMapping("/query/{id}")
    public JsonVO<GoodsDTO> query(@PathVariable("id") Long id){
        GoodsDTO goodsDTO = goodsService.queryById(id);
        if (goodsDTO == null) {
            return JsonVO.fail("id错误或者商品不存在");
        }
        return JsonVO.success(goodsDTO, "商品查询成功");
    }

    /**
     * 下架或审核二手商品
     */
    @ApiOperation("下架或审核二手商品")
    @PutMapping("/update-status")
    public JsonVO<String> updateStatus(@Validated @RequestBody UpdateGoodsStatusDTO updateStatusDTO){
        // 参数校验
        if (updateStatusDTO.getId() == null || updateStatusDTO.getStatus() == null) {
            return JsonVO.fail("商品ID和状态不能为空");
        }

        // 调用服务层方法更新商品状态
        boolean success = goodsService.updateGoodsStatus(updateStatusDTO);
        if (success) {
            return JsonVO.success("商品状态更新成功");
        } else {
            return JsonVO.fail("商品状态更新失败");
        }
    }
}
