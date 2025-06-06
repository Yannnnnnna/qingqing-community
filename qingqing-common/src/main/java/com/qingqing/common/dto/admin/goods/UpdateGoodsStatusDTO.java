package com.qingqing.common.dto.admin.goods;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * 修改商品状态
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel(description = "修改商品状态传输模型")
public class UpdateGoodsStatusDTO {

    /**
     * 商品ID（雪花算法生成的 ID）
     */
    @NotNull(message = "商品ID不能为空")
    @Positive(message = "商品ID必须为正数")
    @ApiModelProperty(value = "商品ID", example = "1930892263544471554", required = true)
    private Long id;

    /**
     * 状态（必须为 0、1、2 或 3）
     */
    @NotNull(message = "商品状态不能为空")
    @Min(value = 0, message = "商品状态不能小于0")
    @Max(value = 3, message = "商品状态不能大于3")
    @ApiModelProperty(value = "商品状态 (0: 未审核, 1: 已上架, 2: 已下架, 3: 已售出)", example = "1", required = true)
    private Integer status;
}
