package com.qingqing.common.query.admin;

import com.qingqing.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * 商品分类查询
 *
 * @author wyr on 2025/6/3
 */
@ApiModel("商品分类分页查询模型")
@Data
public class CategoryPageQuery extends PageQuery {

    /**
     * 分类名称（可选，最大长度50）
     */
    @Size(max = 50, message = "分类名称不能超过50个字符")
    @ApiModelProperty(value = "分类名称", example = "家用电器")
    private String name;

    /**
     * 状态（可选，只能为0或1）
     */
    @Pattern(regexp = "^(0|1)?$", message = "状态必须为0或1")
    @ApiModelProperty(value = "状态 (0-正常,1-禁用)", example = "1")
    private Integer status;
}
