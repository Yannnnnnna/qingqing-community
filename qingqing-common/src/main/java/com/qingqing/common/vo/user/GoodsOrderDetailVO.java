package com.qingqing.common.vo.user;

/**
 * @author wyr on 2025/6/6
 */


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel("订单详情VO")
public class GoodsOrderDetailVO {
    @ApiModelProperty(value = "订单ID", example = "1790123456789012011")
    private Long id;

    @ApiModelProperty(value = "商品ID", example = "1786543210987654322")
    private Long goodsId;

    @ApiModelProperty(value = "商品标题", example = "二手手机出售")
    private String goodsTitle;

    @ApiModelProperty(value = "商品图片列表", example = "[\"http://localhost:8080/imgs/goods/uuid1.jpg\"]")
    private List<String> goodsImages;

    @ApiModelProperty(value = "买家ID", example = "12345")
    private Long buyerId;

    @ApiModelProperty(value = "买家昵称", example = "小明")
    private String buyerName;

    @ApiModelProperty(value = "卖家ID", example = "67890")
    private Long sellerId;

    @ApiModelProperty(value = "卖家昵称", example = "小红")
    private String sellerName;

    @ApiModelProperty(value = "成交价格", example = "1999.99")
    private BigDecimal price;

    @ApiModelProperty(value = "订单状态", example = "1")
    private Integer status;

    @ApiModelProperty(value = "订单状态描述", example = "已支付")
    private String statusDesc;

    @ApiModelProperty(value = "创建时间", example = "2025-06-06 10:30:00")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", example = "2025-06-06 11:00:00")
    private LocalDateTime updateTime;
}
