package com.qingqing.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingqing.common.entity.GoodsOrder;
import com.qingqing.common.dto.user.BuyGoodsDTO;
import com.qingqing.common.dto.user.UpdateOrderStatusDTO;
import com.qingqing.common.vo.user.GoodsOrderDetailVO;
import com.qingqing.common.vo.user.GoodsOrderListVO;
import java.util.List;

public interface GoodsOrderService extends IService<GoodsOrder> {

    /**
     * 购买商品（创建订单）
     * @param buyDTO 购买信息
     * @param buyerId 买家ID
     * @return 订单ID
     */
    Long buyGoods(BuyGoodsDTO buyDTO, Long buyerId);

    /**
     * 查看我卖出的订单（作为卖家）
     * @param sellerId 卖家ID
     * @return 卖出订单列表
     */
    List<GoodsOrderListVO> getMySoldOrders(Long sellerId);

    /**
     * 查看我购买的订单（作为买家）
     * @param buyerId 买家ID
     * @return 购买订单列表
     */
    List<GoodsOrderListVO> getMyBoughtOrders(Long buyerId);

    /**
     * 查看订单详情
     * @param orderId 订单ID
     * @return 订单详情
     */
    GoodsOrderDetailVO getOrderDetail(Long orderId);

    /**
     * 修改订单状态
     * @param updateDTO 更新信息
     * @param currentUserId 当前操作用户ID
     */
    void updateOrderStatus(UpdateOrderStatusDTO updateDTO, Long currentUserId);
}