package com.qingqing.common.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * 更新商品
 *
 * @author wyr on 2025/6/7
 */
@ApiModel("更新商品请求")
@Data
public class SecondHandGoodsUpdateDTO{
    @ApiModelProperty(value = "商品ID", required = true)
    private Long goodsId;

    @ApiModelProperty(value = "商品分类名称", example = "家用电器", required = true)
    @Size(max = 50, message = "商品分类名称长度不能超过50个字符")
    private String categoryName;

    @ApiModelProperty(value = "商品名称", example = "美的空调", required = true)
    @Size(max = 100, message = "商品名称长度不能超过100个字符")
    private String title;

    @ApiModelProperty(value = "商品描述", example = "美的空调新旧程度为9成新")
    @Size(max = 500, message = "商品描述长度不能超过500个字符")
    private String description;

    @ApiModelProperty(value = "商品价格", example = "5000.00", required = true)
    @DecimalMin(value = "0.01", message = "商品价格必须大于0")
    private BigDecimal price;

    @ApiModelProperty(value = "商品图片URL", example = "[\"https://example.com/image1.jpg\"]")
    @Size(min = 1, message = "商品图片URL列表至少包含一张图片")
    private List<String> imageUrls;

    @ApiModelProperty(value = "商品状态", example = "1", notes = "0-待审核,1-已上架,2-已下架,3-已售出")
    private Integer status;
}
