package com.qingqing.admin.controller;


import com.qingqing.admin.service.GoodsOrderService;
import com.qingqing.admin.service.impl.GoodsOrderServiceImpl;
import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.goods_order.GoodsOrderDTO;
import com.qingqing.common.dto.admin.goods_order.GoodsOrderPageDTO;
import com.qingqing.common.dto.admin.goods_order.UpdateOrderStatus;
import com.qingqing.common.query.admin.GoodsOrderPageQuery;
import com.qingqing.common.vo.JsonVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 商品订单表 前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@RestController
@RequestMapping("/admin/goods-order")
@Api(tags = "商品订单管理")
public class GoodsOrderController {
    @Autowired
    private GoodsOrderService goodsOrderService;
    /**
     * 获取商品订单列表（条件+分页）
     */
    @ApiOperation("获取商品订单列表（条件+分页）")
    @GetMapping("/query-all")
    public JsonVO<PageDTO<GoodsOrderPageDTO>> queryAll(GoodsOrderPageQuery goodsOrderPageQuery) {
        PageDTO<GoodsOrderPageDTO> pageDTO = goodsOrderService.queryAll(goodsOrderPageQuery);
        if (pageDTO == null || pageDTO.getRows() == null || pageDTO.getRows().isEmpty()) {
            return JsonVO.fail("没有查询到商品订单");
        }
        return JsonVO.success(pageDTO, "商品订单列表查询成功");
    }
    /**
     * 获取单个商品订单详细
     */
    @ApiOperation("获取单个商品订单详细")
    @GetMapping("/query/{id}")
    public JsonVO<GoodsOrderDTO> query(@PathVariable Long id) {
        GoodsOrderDTO goodsOrderDTO = goodsOrderService.queryById(id);
        if (goodsOrderDTO == null) {
            return JsonVO.fail("没有查询到商品订单");
        }
        return JsonVO.success(goodsOrderDTO, "商品订单查询成功");
    }
    /**
     * 修改商品订单状态
     */
    @ApiOperation("修改商品订单状态")
    @PutMapping("/update-status")
    public JsonVO<String> updateStatus(@RequestBody UpdateOrderStatus updateOrderStatus) {
        boolean isUpdated = goodsOrderService.updateOrderStatus(updateOrderStatus);
        if (!isUpdated) {
            return JsonVO.fail("修改商品订单状态失败");
        }
        return JsonVO.success("商品订单状态修改成功");
    }
}
