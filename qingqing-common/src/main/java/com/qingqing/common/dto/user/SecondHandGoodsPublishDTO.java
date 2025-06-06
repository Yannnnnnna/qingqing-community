package com.qingqing.common.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(value = "二手商品发布DTO")
public class SecondHandGoodsPublishDTO {
    @ApiModelProperty(value = "发布者ID", example = "1708892123456789012", required = true)
    private Long publisherId;
    @ApiModelProperty(value = "商品分类名称", example = "家用电器", required = true)
    private String categoryName;
    @ApiModelProperty(value = "商品名称", example = "美的空调", required = true)
    private String title;
    @ApiModelProperty(value = "商品描述", example = "美的空调新旧程度为9成新")
    private String description;
    @ApiModelProperty(value = "商品价格", example = "5000.00", required = true)
    private BigDecimal price;
    @ApiModelProperty(value = "商品图片URL列表", example = "[\"https://example.com/image1.jpg\",\"https://example.com/image2.jpg\"]")
    private List<String> imageUrls; // 前端提交的是已生成的URL列表


} 