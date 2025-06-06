package com.qingqing.common.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("修改订单状态DTO")
public class UpdateOrderStatusDTO {

    @ApiModelProperty(value = "订单ID", required = true, example = "1790123456789012011")
    @NotNull(message = "订单ID不能为空")
    private Long orderId;

    @ApiModelProperty(value = "订单状态", required = true, example = "3")
    @NotNull(message = "订单状态不能为空")
    @Min(value = 1, message = "订单状态必须为0-4之间的整数")
    @Max(value = 4, message = "订单状态必须为0-4之间的整数")
    private Integer status;
}