package com.qingqing.common.query.user;

import com.qingqing.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品分页查询
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel(description = "商品查询条件")
public class GoodsQuery {

    @ApiModelProperty(value = "发布者名字", example = "张三")
    private String publisherName;

    @ApiModelProperty(value = "类别名", example = "家用电器")
    private String categoryName;

    @ApiModelProperty(value = "商品标题", example = "Java编程思想")
    private String title;



}
