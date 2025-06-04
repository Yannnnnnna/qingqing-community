package com.qingqing.common.query.admin;

import com.qingqing.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 商品分类查询
 *
 * @author wyr on 2025/6/3
 */
@ApiModel("商品分类分页查询模型")
@Data
public class CategoryPageQuery extends PageQuery {

    @ApiModelProperty(value = "分类名称", example = "家用电器")
    private String name;
    @ApiModelProperty(value = "状态 (0-正常,1-禁用)", example = "1")
    private Integer status;
}
