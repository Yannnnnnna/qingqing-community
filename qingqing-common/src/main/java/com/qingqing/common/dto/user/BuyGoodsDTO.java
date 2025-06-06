package com.qingqing.common.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Data
@ApiModel("购买商品请求DTO")
public class BuyGoodsDTO {
    @ApiModelProperty(value = "商品ID", required = true, example = "1786543210987654322")
    @NotNull(message = "商品ID不能为空")
    private Long goodsId;

    @ApiModelProperty(value = "成交价格", required = true, example = "1999.99")
    @NotNull(message = "成交价格不能为空")
    @DecimalMin(value = "0.01", message = "成交价格必须大于0")
    private BigDecimal price;
}
