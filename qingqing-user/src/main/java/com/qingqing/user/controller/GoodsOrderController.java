package com.qingqing.user.controller;




import com.qingqing.common.dto.user.BuyGoodsDTO;
import com.qingqing.common.dto.user.UpdateOrderStatusDTO;
import com.qingqing.common.utils.BaseContext;
import com.qingqing.common.utils.JwtUtil;
import com.qingqing.common.vo.JsonVO;
import com.qingqing.common.vo.user.GoodsOrderDetailVO;
import com.qingqing.common.vo.user.GoodsOrderListVO;
import com.qingqing.user.service.GoodsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 商品订单表 前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@Slf4j
@RestController
@RequestMapping("/user/second-hand-orders")
@Api(tags = "商品订单管理")
public class GoodsOrderController {
    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private JwtUtil jwtUtil;
    /**
     * 购买商品（创建订单）
     */
    @ApiOperation("购买商品（创建订单）")
    @PostMapping("/buy")
    public JsonVO<Long> buyGoods(@Valid @RequestBody BuyGoodsDTO buyDTO) {
        try {
            // 从JWT令牌中获取买家ID
            Long buyerId = BaseContext.getCurrentId();

            // 创建订单
            Long orderId = goodsOrderService.buyGoods(buyDTO, buyerId);

            return JsonVO.success(orderId, "订单创建成功");

        } catch (IllegalArgumentException e) {
            log.warn("购买商品参数异常：{}", e.getMessage());
            return JsonVO.fail(e.getMessage());
        } catch (Exception e) {
            log.error("购买商品发生未知错误：{}", e.getMessage(), e);
            return JsonVO.fail("购买失败，请稍后再试");
        }
    }

    /**
     * 查看我卖出的订单（作为卖家）
     */
    @ApiOperation("查看我卖出的订单")
    @GetMapping("/my-sold")
    public JsonVO<List<GoodsOrderListVO>> getMySoldOrders() {
        try {
            Long sellerId =  BaseContext.getCurrentId();
            List<GoodsOrderListVO> orders = goodsOrderService.getMySoldOrders(sellerId);
            return JsonVO.success(orders, "查询成功");

        } catch (Exception e) {
            log.error("查询卖出订单发生错误：{}", e.getMessage(), e);
            return JsonVO.fail("查询失败，请稍后再试");
        }
    }

    /**
     * 查看我购买的订单（作为买家）
     */
    @ApiOperation("查看我购买的订单")
    @GetMapping("/my-bought")
    public JsonVO<List<GoodsOrderListVO>> getMyBoughtOrders() {
        try {
            Long buyerId =  BaseContext.getCurrentId();
            List<GoodsOrderListVO> orders = goodsOrderService.getMyBoughtOrders(buyerId);
            return JsonVO.success(orders, "查询成功");

        } catch (Exception e) {
            log.error("查询购买订单发生错误：{}", e.getMessage(), e);
            return JsonVO.fail("查询失败，请稍后再试");
        }
    }

    /**
     * 查看订单详情
     */
    @ApiOperation("查看订单详情")
    @GetMapping("/{id}")
    public JsonVO<GoodsOrderDetailVO> getOrderDetail(@PathVariable("id") Long id) {
        try {
            GoodsOrderDetailVO detail = goodsOrderService.getOrderDetail(id);
            return JsonVO.success(detail, "查询成功");

        } catch (IllegalArgumentException e) {
            log.warn("查询订单详情参数异常：{}", e.getMessage());
            return JsonVO.fail(e.getMessage());
        } catch (Exception e) {
            log.error("查询订单详情发生错误：{}", e.getMessage(), e);
            return JsonVO.fail("查询失败，请稍后再试");
        }
    }

    /**
     * 修改订单状态
     */
    @ApiOperation("修改订单状态")
    @PutMapping("/status")
    public JsonVO<String> updateOrderStatus(@Valid @RequestBody UpdateOrderStatusDTO updateDTO) {
        try {
            Long currentUserId =  BaseContext.getCurrentId();
            goodsOrderService.updateOrderStatus(updateDTO, currentUserId);
            return JsonVO.success("订单状态更新成功");

        } catch (IllegalArgumentException e) {
            log.warn("更新订单状态参数异常：{}", e.getMessage());
            return JsonVO.fail(e.getMessage());
        } catch (Exception e) {
            log.error("更新订单状态发生错误：{}", e.getMessage(), e);
            return JsonVO.fail("更新失败，请稍后再试");
        }
    }

}
