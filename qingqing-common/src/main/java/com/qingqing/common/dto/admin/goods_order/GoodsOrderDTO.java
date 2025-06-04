package com.qingqing.common.dto.admin.goods_order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 订单详细信息

 * @author wyr on 2025/6/3
 */
@Data
@ApiModel(description = "订单详细信息")
public class GoodsOrderDTO {
    @ApiModelProperty(value = "订单编号（主键）", example = "雪花生成的id")
    private Long id;

    // 买家信息
    @ApiModelProperty(value = "买家昵称", example = "用户_123")
    private String buyerNickname;

    @ApiModelProperty(value = "买家手机号", example = "13800001111")
    private String buyerPhone;

    @ApiModelProperty(value = "买家居住地址", example = "浙江省杭州市西湖区文三路123号")
    private String buyerAddress;

    // 卖家信息
    @ApiModelProperty(value = "卖家昵称", example = "用户_456")
    private String sellerNickname;

    @ApiModelProperty(value = "卖家手机号", example = "13900002222")
    private String sellerPhone;

    @ApiModelProperty(value = "卖家居住地址", example = "浙江省杭州市余杭区五常大道789号")
    private String sellerAddress;

    // 成交信息
    @ApiModelProperty(value = "成交价格", example = "59.9")
    private Double dealPrice;

    @ApiModelProperty(value = "订单状态 (0-待支付,1-已支付,2-已发货,3-已完成,4-已取消)", example = "1")
    private Integer status;

    @ApiModelProperty(value = "成交时间", example = "2025-06-03 15:30:00")
    private LocalDateTime dealTime;

    @ApiModelProperty(value = "更新时间", example = "2025-06-03 16:00:00")
    private LocalDateTime updateTime;

    // 商品信息
    @ApiModelProperty(value = "商品名称", example = "Java编程思想")
    private String goodsName;

    @ApiModelProperty(value = "商品价格", example = "59.9")
    private Double goodsPrice;

    @ApiModelProperty(value = "商品图片URL", example = "https://example.com/images/java_book.jpg")
    private String goodsImage;

    @ApiModelProperty(value = "商品描述", example = "全新正版书籍，适合Java初学者和进阶者阅读。")
    private String goodsDescription;
}
