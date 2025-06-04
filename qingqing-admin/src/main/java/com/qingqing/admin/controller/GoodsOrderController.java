package com.qingqing.admin.controller;


import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.goods_order.GoodsOrderDTO;
import com.qingqing.common.dto.admin.goods_order.GoodsOrderPageDTO;
import com.qingqing.common.dto.admin.goods_order.UpdateOrderStatus;
import com.qingqing.common.query.admin.GoodsOrderPageQuery;
import com.qingqing.common.vo.JsonVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/goods-order")
@Api(tags = "商品订单管理")
public class GoodsOrderController {
    /**
     * 获取商品订单列表（条件+分页）
     */
    @ApiOperation("获取商品订单列表（条件+分页）")
    @GetMapping("/query-all")
    public JsonVO<PageDTO<GoodsOrderPageDTO>> queryAll(GoodsOrderPageQuery goodsOrderPageQuery) {
        return null;
    }
    /**
     * 获取单个商品订单详细
     */
    @ApiOperation("获取单个商品订单详细")
    @GetMapping("/query/{id}")
    public JsonVO<GoodsOrderDTO> query(@PathVariable Long id) {
        return null;
    }
    /**
     * 修改商品订单状态
     */
    @ApiOperation("修改商品订单状态")
    @GetMapping("/update-status")
    public JsonVO<String> updateStatus(@RequestBody UpdateOrderStatus updateOrderStatus) {
        return null;
    }
}
