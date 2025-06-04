package com.qingqing.common.dto.admin.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 修改商品状态
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel(description = "修改商品状态传输模型")
public class UpdateGoodsStatusDTO {
    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID", example = "雪花id")
    private Long id;

    /**
     * 状态
     * 0: 未审核
     * 1: 已上架
     * 2: 已下架
     * 3: 已售出
     */
    @ApiModelProperty(value = "商品状态 (0: 未审核, 1: 已上架, 2: 已下架, 3: 已售出)", example = "1")
    private Integer status;
}
