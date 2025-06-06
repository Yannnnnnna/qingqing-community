package com.qingqing.admin.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference; // 用于复杂泛型类型转换
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingqing.admin.mapper.GoodsOrderMapper;
import com.qingqing.admin.service.GoodsOrderService;
import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.goods_order.GoodsOrderDTO;
import com.qingqing.common.dto.admin.goods_order.GoodsOrderPageDTO;
import com.qingqing.common.dto.admin.goods_order.UpdateOrderStatus;
import com.qingqing.common.entity.GoodsOrder;
import com.qingqing.common.query.admin.GoodsOrderPageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品订单表 服务实现类
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@Service
public class GoodsOrderServiceImpl extends ServiceImpl<GoodsOrderMapper, GoodsOrder> implements GoodsOrderService {
    @Autowired
    private GoodsOrderMapper goodsOrderMapper;

    /**
     * 获取商品订单列表（条件+分页）
     *
     * @param goodsOrderPageQuery 查询条件和分页信息
     * @return
     */
    @Override
    public PageDTO<GoodsOrderPageDTO> queryAll(GoodsOrderPageQuery goodsOrderPageQuery) {
        // 1. 创建 Mybatis-Plus 的分页对象
        // goodsOrderPageQuery.getPageIndex() 是当前页码 (PageQuery 默认通常是 pageNum/pageIndex)
        // goodsOrderPageQuery.getPageSize() 是每页记录数
        Page<GoodsOrder> page = new Page<>(goodsOrderPageQuery.getPageIndex(), goodsOrderPageQuery.getPageSize());

        // 2. 调用 GoodsOrderMapper 中定义的联表分页查询方法
        IPage<GoodsOrderPageDTO> orderPage = goodsOrderMapper.selectGoodsOrderPage(
                page,
                goodsOrderPageQuery.getGoodsName(),
                goodsOrderPageQuery.getBuyerName(),
                goodsOrderPageQuery.getSellerName(),
                goodsOrderPageQuery.getStatus()
        );

        // 3. 将 Mybatis-Plus 的 IPage 结果转换为自定义的 PageDTO
        PageDTO<GoodsOrderPageDTO> resultPageDTO = new PageDTO<>();
        resultPageDTO.setRows(orderPage.getRecords()); // 设置查询到的数据列表
        resultPageDTO.setTotal(orderPage.getTotal());     // 设置总记录数
        resultPageDTO.setPageIndex(orderPage.getCurrent()); // 设置当前页码
        resultPageDTO.setPageSize(orderPage.getSize());       // 设置每页记录数
        resultPageDTO.setPages(orderPage.getPages());     // 设置总页数

        return resultPageDTO;
    }


    /**
     * 获取单个商品订单详细信息
     * 通过自定义XML联表查询实现
     * @param id 商品订单ID
     * @return GoodsOrderDTO
     */
    @Override
    public GoodsOrderDTO queryById(Long id) {
        GoodsOrderDTO goodsOrderDTO = goodsOrderMapper.selectGoodsOrderDetailById(id);

        if (goodsOrderDTO != null) {
            // 处理商品图片URL
            String imagesJson = goodsOrderDTO.getGoodsImage(); // 从DTO获取JSON字符串

            if (imagesJson != null && !imagesJson.isEmpty()) {
                try {
                    // 使用 Fastjson 将 JSON 字符串解析为 List<String>
                    // TypeReference 用于处理泛型类型，确保正确解析 List<String>
                    List<String> imageUrls = JSON.parseObject(imagesJson, new TypeReference<List<String>>() {});

                    if (imageUrls != null && !imageUrls.isEmpty()) {
                        // 假设我们只需要第一张图片
                        goodsOrderDTO.setGoodsImage(imageUrls.get(0));
                    } else {
                        // 如果解析结果为空列表，则设置图片为空
                        goodsOrderDTO.setGoodsImage(null);
                    }
                } catch (Exception e) {
                    // 捕获 Fastjson 解析异常
                    System.err.println("图片JSON转换失败: " + imagesJson + ", Error: " + e.getMessage());
                    // 转换失败时，可以选择设置图片为空或一个默认值
                    goodsOrderDTO.setGoodsImage(null);
                }
            } else {
                // 如果 imagesJson 本身就是 null 或空字符串
                goodsOrderDTO.setGoodsImage(null);
            }
        }
        return goodsOrderDTO;
    }

    /**
     * 修改商品订单状态
     * @param updateOrderStatus
     * @return
     */
    @Override
    public boolean updateOrderStatus(UpdateOrderStatus updateOrderStatus) {
        GoodsOrder goodsOrder = new GoodsOrder();
        goodsOrder.setId(updateOrderStatus.getId());
        goodsOrder.setStatus(updateOrderStatus.getStatus());
        // 调用 Mybatis-Plus 的 updateById 方法更新订单状态
        int rows = goodsOrderMapper.updateById(goodsOrder);
        // 如果更新成功，返回 true，否则返回 false
        return rows > 0;
    }

}
