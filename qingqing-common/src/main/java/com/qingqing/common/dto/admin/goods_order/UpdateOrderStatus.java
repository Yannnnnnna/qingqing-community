package com.qingqing.common.dto.admin.goods_order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品订单状态更新 DTO
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel(description = "商品订单状态更新 DTO")
public class UpdateOrderStatus {
    @ApiModelProperty(value = "订单ID", required = true, example = "雪花id")
    private Long id;
    @ApiModelProperty(value = "状态 0-待支付,1-已支付,2-已发货,3-已完成,4-已取消", required = true, example = "1")
    private Integer status;
}
