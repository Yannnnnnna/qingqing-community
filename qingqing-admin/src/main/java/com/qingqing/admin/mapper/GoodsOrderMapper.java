package com.qingqing.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qingqing.common.dto.admin.goods_order.GoodsOrderDTO;
import com.qingqing.common.dto.admin.goods_order.GoodsOrderPageDTO;
import com.qingqing.common.entity.GoodsOrder;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品订单表 Mapper 接口
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
public interface GoodsOrderMapper extends BaseMapper<GoodsOrder> {
    /**
     * 分页查询商品订单，并关联查询商品名、买家名和卖家名
     * @param page 分页对象，由 Mybatis-Plus 自动处理分页逻辑
     * @param goodsName 商品名称 (模糊查询)
     * @param buyerName 买家名字 (模糊查询)
     * @param sellerName 卖家名字 (模糊查询)
     * @param status 订单状态
     * @return 分页结果的 IPage<GoodsOrderPageDTO>
     */
    IPage<GoodsOrderPageDTO> selectGoodsOrderPage(IPage<GoodsOrder> page,
                                                  @Param("goodsName") String goodsName,
                                                  @Param("buyerName") String buyerName,
                                                  @Param("sellerName") String sellerName,
                                                  @Param("status") Integer status);


    /**
     * 根据订单ID查询商品订单的详细信息，包括买家、卖家和商品信息。
     * @param id 商品订单ID
     * @return GoodsOrderDTO 包含所有详细信息的对象
     */
    GoodsOrderDTO selectGoodsOrderDetailById(@Param("id") Long id);
}
