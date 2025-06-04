package com.qingqing.common.query.admin;

import com.qingqing.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品订单分页查询
 *
 * @author wyr on 2025/6/3
 */

@Data
@ApiModel(description = "商品订单分页查询条件")
public class GoodsOrderPageQuery extends PageQuery {
    @ApiModelProperty(value = "商品名称", example = "Java编程思想")
    private String goodsName;

    @ApiModelProperty(value = "买家名字", example = "张三")
    private String buyerName;

    @ApiModelProperty(value = "卖家名字", example = "李四")
    private String sellerName;

    @ApiModelProperty(value = "订单状态 (0-待支付,1-已支付,2-已发货,3-已完成,4-已取消)", example = "1")
    private Integer status;
}

