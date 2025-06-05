package com.qingqing.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.goods.GoodsDTO;
import com.qingqing.common.dto.admin.goods.GoodsPageDTO;
import com.qingqing.common.dto.admin.goods.UpdateGoodsStatusDTO;
import com.qingqing.common.entity.Goods;
import com.qingqing.common.query.admin.GoodsPageQuery;

/**
 * <p>
 * 二手商品表 服务类
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
public interface GoodsService extends IService<Goods> {
    /**
     * 获取二手商品列表（条件+分页）
     * @param goodsPageQuery
     * @return
     */
    PageDTO<GoodsPageDTO> queryAll(GoodsPageQuery goodsPageQuery);
    /** 根据ID查询单个二手商品详细信息
     * @param id 商品ID
     * @return GoodsDTO 商品详细信息
     */
    GoodsDTO queryById(Long id);

    /**
     * 下架或审核二手商品
     * @param updateStatusDTO
     * @return
     */
    boolean updateGoodsStatus(UpdateGoodsStatusDTO updateStatusDTO);
}
