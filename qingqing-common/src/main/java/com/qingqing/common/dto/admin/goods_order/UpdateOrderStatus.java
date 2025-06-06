package com.qingqing.common.dto.admin.goods_order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * 商品订单状态更新 DTO
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel(description = "商品订单状态更新 DTO")
public class UpdateOrderStatus {

    /**
     * 订单ID（雪花算法生成的 ID）
     */
    @NotNull(message = "订单ID不能为空")
    @Positive(message = "订单ID必须为正数")
    @ApiModelProperty(value = "订单ID", required = true, example = "1790123456789012010")
    private Long id;

    /**
     * 状态（必须为 0、1、2、3 或 4）
     */
    @NotNull(message = "订单状态不能为空")
    @Min(value = 0, message = "订单状态不能小于0")
    @Max(value = 4, message = "订单状态不能大于4")
    @ApiModelProperty(value = "状态 0-待支付,1-已支付,2-已发货,3-已完成,4-已取消", required = true, example = "1")
    private Integer status;
}
