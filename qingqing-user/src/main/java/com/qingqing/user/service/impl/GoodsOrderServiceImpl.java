package com.qingqing.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingqing.common.entity.Goods;
import com.qingqing.common.entity.GoodsOrder;
import com.qingqing.common.entity.User;
import com.qingqing.common.dto.user.BuyGoodsDTO;
import com.qingqing.common.dto.user.UpdateOrderStatusDTO;
import com.qingqing.common.utils.JsonUtils;
import com.qingqing.common.vo.user.GoodsOrderDetailVO;
import com.qingqing.common.vo.user.GoodsOrderListVO;
import com.qingqing.user.mapper.GoodsMapper;
import com.qingqing.user.mapper.GoodsOrderMapper;
import com.qingqing.user.mapper.UserMapper;
import com.qingqing.user.service.GoodsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GoodsOrderServiceImpl extends ServiceImpl<GoodsOrderMapper, GoodsOrder> implements GoodsOrderService {

    @Autowired
    private GoodsOrderMapper goodsOrderMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     *
     * @param buyDTO 购买信息
     * @param buyerId 买家ID
     * @return
     */
    @Override
    @Transactional
    public Long buyGoods(BuyGoodsDTO buyDTO, Long buyerId) {
        // 1. 参数校验
        if (buyDTO == null || buyerId == null) {
            throw new IllegalArgumentException("参数不能为空");
        }

        // 2. 查询商品信息
        Goods goods = goodsMapper.selectById(buyDTO.getGoodsId());
        if (goods == null) {
            throw new RuntimeException("商品不存在");
        }

        // 3. 验证商品状态
        if (goods.getStatus() != 1) {
            throw new RuntimeException("商品未上架或已售出");
        }

        // 4. 验证买家不能购买自己的商品
        if (Objects.equals(goods.getUserId(), buyerId)) {
            throw new RuntimeException("不能购买自己发布的商品");
        }

        // 5. 创建订单
        GoodsOrder order = new GoodsOrder();
        order.setGoodsId(buyDTO.getGoodsId());
        order.setBuyerId(buyerId);
        order.setSellerId(goods.getUserId());
        order.setPrice(buyDTO.getPrice());
        order.setStatus(0); // 待支付

        // 6. 保存订单
        int result = goodsOrderMapper.insert(order);
        if (result == 0) {
            throw new RuntimeException("订单创建失败");
        }

        // 7. 更新商品状态为已售出
        goods.setStatus(3);
        goodsMapper.updateById(goods);

        return order.getId();
    }

    /**
     * 获取我的已售订单
     * @param sellerId 卖家ID
     * @return
     */
    @Override
    public List<GoodsOrderListVO> getMySoldOrders(Long sellerId) {
        if (sellerId == null) {
            throw new IllegalArgumentException("卖家ID不能为空");
        }

        // 1. 查询作为卖家的订单
        LambdaQueryWrapper<GoodsOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GoodsOrder::getSellerId, sellerId)
                .orderByDesc(GoodsOrder::getCreateTime);

        List<GoodsOrder> orders = goodsOrderMapper.selectList(wrapper);

        return convertToOrderListVO(orders, true);
    }

    /**
     * 获取我的已买到订单
     * @param buyerId 买家ID
     * @return
     */
    @Override
    public List<GoodsOrderListVO> getMyBoughtOrders(Long buyerId) {
        if (buyerId == null) {
            throw new IllegalArgumentException("买家ID不能为空");
        }

        // 1. 查询作为买家的订单
        LambdaQueryWrapper<GoodsOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GoodsOrder::getBuyerId, buyerId)
                .orderByDesc(GoodsOrder::getCreateTime);

        List<GoodsOrder> orders = goodsOrderMapper.selectList(wrapper);

        return convertToOrderListVO(orders, false);
    }

    /**
     * 展示订单详情
     * @param orderId 订单ID
     * @return
     */
    @Override
    public GoodsOrderDetailVO getOrderDetail(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("订单ID不能为空");
        }

        // 1. 查询订单信息
        GoodsOrder order = goodsOrderMapper.selectById(orderId);
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        // 2. 查询商品信息
        Goods goods = goodsMapper.selectById(order.getGoodsId());

        // 3. 查询买家和卖家信息
        User buyer = userMapper.selectById(order.getBuyerId());
        User seller = userMapper.selectById(order.getSellerId());

        // 4. 组装VO
        GoodsOrderDetailVO vo = new GoodsOrderDetailVO();
        vo.setId(order.getId());
        vo.setGoodsId(order.getGoodsId());
        vo.setBuyerId(order.getBuyerId());
        vo.setSellerId(order.getSellerId());
        vo.setPrice(order.getPrice());
        vo.setStatus(order.getStatus());
        vo.setStatusDesc(getStatusDescription(order.getStatus()));
        vo.setCreateTime(order.getCreateTime());
        vo.setUpdateTime(order.getUpdateTime());

        if (goods != null) {
            vo.setGoodsTitle(goods.getTitle());
            // 使用JsonUtils处理商品图片
            List<String> imageList = JsonUtils.parseJsonToStringList(goods.getImages());
            vo.setGoodsImages(imageList);
        }

        if (buyer != null) {
            vo.setBuyerName(buyer.getNickname());
        }

        if (seller != null) {
            vo.setSellerName(seller.getNickname());
        }

        return vo;
    }

    /**
     * 更新订单状态
     * @param updateDTO 更新信息
     * @param currentUserId 当前操作用户ID
     */
    @Override
    @Transactional
    public void updateOrderStatus(UpdateOrderStatusDTO updateDTO, Long currentUserId) {
        if (updateDTO == null || currentUserId == null) {
            throw new IllegalArgumentException("参数不能为空");
        }

        // 1. 查询订单
        GoodsOrder order = goodsOrderMapper.selectById(updateDTO.getOrderId());
        if (order == null) {
            throw new RuntimeException("订单不存在");
        }

        // 2. 验证操作权限和状态流转规则
        validateStatusUpdate(order, updateDTO.getStatus(), currentUserId);

        // 3. 更新订单状态
        order.setStatus(updateDTO.getStatus());
        int result = goodsOrderMapper.updateById(order);
        if (result == 0) {
            throw new RuntimeException("订单状态更新失败");
        }
    }

    /**
     * 验证状态更新的权限和规则
     */
    private void validateStatusUpdate(GoodsOrder order, Integer newStatus, Long currentUserId) {
        Integer currentStatus = order.getStatus();

        // 验证操作权限
        boolean isBuyer = Objects.equals(order.getBuyerId(), currentUserId);
        boolean isSeller = Objects.equals(order.getSellerId(), currentUserId);

        if (!isBuyer && !isSeller) {
            throw new RuntimeException("无权限操作此订单");
        }

        // 验证状态流转规则
        switch (newStatus) {
            case 1: // 已支付 - 只有买家可以操作
                if (!isBuyer || currentStatus != 0) {
                    throw new RuntimeException("订单状态更新失败：无效的状态流转");
                }
                break;
            case 2: // 已发货 - 只有卖家可以操作
                if (!isSeller || currentStatus != 1) {
                    throw new RuntimeException("订单状态更新失败：无效的状态流转");
                }
                break;
            case 3: // 已完成 - 只有买家可以操作（确认收货）
                if (!isBuyer || currentStatus != 2) {
                    throw new RuntimeException("订单状态更新失败：无效的状态流转");
                }
                break;
            case 4: // 已取消 - 买卖双方都可以操作（限定条件下）
                if (currentStatus > 1) {
                    throw new RuntimeException("订单已发货，无法取消");
                }
                break;
            default:
                throw new RuntimeException("无效的订单状态");
        }
    }

    /**
     * 转换为订单列表VO
     */
    private List<GoodsOrderListVO> convertToOrderListVO(List<GoodsOrder> orders, boolean isSeller) {
        if (orders == null || orders.isEmpty()) {
            return Collections.emptyList();
        }

        // 批量查询商品信息
        List<Long> goodsIds = orders.stream()
                .map(GoodsOrder::getGoodsId)
                .distinct()
                .collect(Collectors.toList());

        Map<Long, Goods> goodsMap = Collections.emptyMap();
        if (!goodsIds.isEmpty()) {
            List<Goods> goodsList = goodsMapper.selectBatchIds(goodsIds);
            goodsMap = goodsList.stream()
                    .collect(Collectors.toMap(Goods::getId, Function.identity()));
        }

        // 批量查询用户信息
        List<Long> userIds = orders.stream()
                .map(order -> isSeller ? order.getBuyerId() : order.getSellerId())
                .distinct()
                .collect(Collectors.toList());

        Map<Long, User> userMap = Collections.emptyMap();
        if (!userIds.isEmpty()) {
            List<User> userList = userMapper.selectBatchIds(userIds);
            userMap = userList.stream()
                    .collect(Collectors.toMap(User::getId, Function.identity()));
        }

        final Map<Long, Goods> finalGoodsMap = goodsMap;
        final Map<Long, User> finalUserMap = userMap;

        return orders.stream().map(order -> {
            GoodsOrderListVO vo = new GoodsOrderListVO();
            vo.setId(order.getId());
            vo.setGoodsId(order.getGoodsId());
            vo.setPrice(order.getPrice());
            vo.setStatus(order.getStatus());
            vo.setStatusDesc(getStatusDescription(order.getStatus()));
            vo.setCreateTime(order.getCreateTime());

            // 设置商品信息
            Goods goods = finalGoodsMap.get(order.getGoodsId());
            if (goods != null) {
                vo.setGoodsTitle(goods.getTitle());
                // 使用JsonUtils获取商品封面图片（第一张图片）
                String coverImage = JsonUtils.getFirstImageFromJson(goods.getImages());
                vo.setGoodsCoverImage(coverImage);
            }

            // 设置对方用户信息
            Long otherUserId = isSeller ? order.getBuyerId() : order.getSellerId();
            vo.setOtherUserId(otherUserId);

            User otherUser = finalUserMap.get(otherUserId);
            if (otherUser != null) {
                vo.setOtherUserName(otherUser.getNickname());
            }

            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 获取状态描述
     */
    private String getStatusDescription(Integer status) {
        if (status == null) {
            return "未知状态";
        }

        switch (status) {
            case 0:
                return "待支付";
            case 1:
                return "已支付";
            case 2:
                return "已发货";
            case 3:
                return "已完成";
            case 4:
                return "已取消";
            default:
                return "未知状态";
        }
    }
}