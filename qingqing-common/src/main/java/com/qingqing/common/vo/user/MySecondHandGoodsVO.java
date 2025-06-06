package com.qingqing.common.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("我的二手商品列表VO")
public class MySecondHandGoodsVO {
    @ApiModelProperty(value = "商品ID", example = "1786543210987654322")
    private Long id;

    @ApiModelProperty(value = "商品封面图片URL", example = "http://localhost:8080/imgs/goods/uuid.jpg")
    private String coverImage;

    @ApiModelProperty(value = "商品标题", example = "二手手机出售")
    private String title;

    @ApiModelProperty(value = "商品价格", example = "1999.99")
    private BigDecimal price;

    @ApiModelProperty(value = "商品状态", example = "1")
    private Integer status;

    @ApiModelProperty(value = "商品状态描述", example = "已上架")
    private String statusDesc;
}
