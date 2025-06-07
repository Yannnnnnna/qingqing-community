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

@Data
@ApiModel(value = "二手商品发布DTO")
public class SecondHandGoodsPublishDTO {

//    @NotNull(message = "发布者ID不能为空")
//    private Long publisherId;

    @ApiModelProperty(value = "商品分类名称", example = "家用电器", required = true)
    @NotBlank(message = "商品分类名称不能为空")
    @Size(max = 50, message = "商品分类名称长度不能超过50个字符")
    private String categoryName;

    @ApiModelProperty(value = "商品名称", example = "美的空调", required = true)
    @NotBlank(message = "商品名称不能为空")
    @Size(max = 100, message = "商品名称长度不能超过100个字符")
    private String title;

    @ApiModelProperty(value = "商品描述", example = "美的空调新旧程度为9成新")
    @Size(max = 500, message = "商品描述长度不能超过500个字符")
    private String description;

    @ApiModelProperty(value = "商品价格", example = "5000.00", required = true)
    @NotNull(message = "商品价格不能为空")
    @DecimalMin(value = "0.01", message = "商品价格必须大于0")
    private BigDecimal price;

    @ApiModelProperty(value = "商品图片URL列表", example = "[\"https://example.com/image1.jpg\",\"https://example.com/image2.jpg\"]")
    @NotNull(message = "商品图片URL列表不能为空")
    @Size(min = 1, message = "商品图片URL列表至少包含一张图片")
    private List<String> imageUrls;
}