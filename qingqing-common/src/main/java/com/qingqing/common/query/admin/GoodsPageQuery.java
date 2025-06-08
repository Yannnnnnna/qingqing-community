package com.qingqing.common.query.admin;

import com.qingqing.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * 商品分页查询
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel(description = "商品分页查询条件")
public class GoodsPageQuery extends PageQuery {

    /**
     * 发布者名字（可选，最大长度50）
     */
    @Size(max = 50, message = "发布者名字不能超过50个字符")
    @ApiModelProperty(value = "发布者名字", example = "张三")
    private String publisherName;

    /**
     * 类别名（可选，最大长度50）
     */
    @Size(max = 50, message = "类别名不能超过50个字符")
    @ApiModelProperty(value = "类别名", example = "家用电器")
    private String categoryName;

    /**
     * 商品标题（可选，最大长度100）
     */
    @Size(max = 100, message = "商品标题不能超过100个字符")
    @ApiModelProperty(value = "商品标题", example = "Java编程思想")
    private String title;

    /**
     * 商品状态（可选，只能为 0, 1, 2, 3）
     */
//    @Pattern(regexp = "^(0|1|2|3)?$", message = "商品状态必须为0、1、2或3")
    @ApiModelProperty(value = "商品状态 (0: 未审核, 1: 审核通过, 2: 已下架, 3: 已售出)", example = "1")
    private Integer status;
}
