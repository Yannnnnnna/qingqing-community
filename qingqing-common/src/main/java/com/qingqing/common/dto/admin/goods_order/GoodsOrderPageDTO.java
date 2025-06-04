package com.qingqing.common.dto.admin.goods_order;

import com.qingqing.common.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wyr on 2025/6/3
 * 订单分页查询结果
 * 订单编号（主键）
 * 商品名
 * 买家名字
 * 卖家名字
 * 成交价格
 * 状态
 * 成交时间
 */
@Data
@ApiModel("订单分页查询结果")
public class GoodsOrderPageDTO {
    @ApiModelProperty(value = "订单编号（主键）", example = "1001")
    private Long id;

    @ApiModelProperty(value = "商品名称", example = "Java编程思想")
    private String goodsName; // 商品名

    @ApiModelProperty(value = "买家名字", example = "张三")
    private String buyerName; // 买家名字

    @ApiModelProperty(value = "卖家名字", example = "李四")
    private String sellerName; // 卖家名字

    @ApiModelProperty(value = "成交价格", example = "59.9")
    private Double price;

    @ApiModelProperty(value = "订单状态 0-待支付,1-已支付,2-已发货,3-已完成,4-已取消", example = "1")
    private Integer status;

    @ApiModelProperty(value = "成交时间", example = "2025-06-03 15:30:00")
    private LocalDateTime dealTime;
}
