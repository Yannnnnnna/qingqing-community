package com.qingqing.common.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("二手商品详细")
public class SecondHandGoodsDetailVO {
    @ApiModelProperty(value = "商品ID", example = "1786543210987654322")
    private Long id;
    @ApiModelProperty(value = "卖家ID", example = "1234567890123456789")
    private Long sellerId;
    @ApiModelProperty(value = "商品图片列表", example = "[\"http://example.com/image1.jpg\", \"http://example.com/image2.jpg\"]")
    private List<String>  imageUrls;
    @ApiModelProperty(value = "商品标题", example = "二手手机出售")
    private String title;
    @ApiModelProperty(value = "商品描述", example = "几乎全新，使用不到半年")
    private String description;
    @ApiModelProperty(value = "商品价格", example = "1999.99")
    private BigDecimal price;

} 