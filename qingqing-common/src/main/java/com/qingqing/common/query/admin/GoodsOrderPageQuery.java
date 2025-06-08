package com.qingqing.common.query.admin;

import com.qingqing.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * 商品订单分页查询
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel(description = "商品订单分页查询条件")
public class GoodsOrderPageQuery extends PageQuery {

    /**
     * 商品名称（可选，最大长度100）
     */
    @Size(max = 100, message = "商品名称不能超过100个字符")
    @ApiModelProperty(value = "商品名称", example = "Java编程思想")
    private String goodsName;

    /**
     * 买家名字（可选，最大长度50）
     */
    @Size(max = 50, message = "买家名字不能超过50个字符")
    @ApiModelProperty(value = "买家名字", example = "张三")
    private String buyerName;

    /**
     * 卖家名字（可选，最大长度50）
     */
    @Size(max = 50, message = "卖家名字不能超过50个字符")
    @ApiModelProperty(value = "卖家名字", example = "李四")
    private String sellerName;

    /**
     * 订单状态（可选，只能为 0, 1, 2, 3, 4）
     */
//    @Pattern(regexp = "^(0|1|2|3|4)?$", message = "订单状态必须为0、1、2、3或4")
    @ApiModelProperty(value = "订单状态 (0-待支付,1-已支付,2-已发货,3-已完成,4-已取消)", example = "1")
    private Integer status;
}
