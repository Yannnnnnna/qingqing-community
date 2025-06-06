package com.qingqing.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.goods_order.GoodsOrderDTO;
import com.qingqing.common.dto.admin.goods_order.GoodsOrderPageDTO;
import com.qingqing.common.dto.admin.goods_order.UpdateOrderStatus;
import com.qingqing.common.entity.GoodsOrder;
import com.qingqing.common.query.admin.GoodsOrderPageQuery;

/**
 * <p>
 * 商品订单表 服务类
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
public interface GoodsOrderService extends IService<GoodsOrder> {
    /**
     * 获取商品订单列表（条件+分页）
     *
     * @param goodsOrderPageQuery 查询条件和分页信息
     * @return 分页查询结果
     */
    PageDTO<GoodsOrderPageDTO> queryAll(GoodsOrderPageQuery goodsOrderPageQuery);

    /**
     * 获取单个商品订单详细信息
     * @param id
     * @return
     */
    GoodsOrderDTO queryById(Long id);

    /**
     * 修改商品订单状态
     * @param updateOrderStatus
     * @return
     */
    boolean updateOrderStatus(UpdateOrderStatus updateOrderStatus);
}
