package com.qingqing.common.query.admin;

import com.qingqing.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理员分页查询
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel("管理员分页查询模型")
public class AdminQuery extends PageQuery {
    @ApiModelProperty(value = "用户名", example = "admin")
    private String userName;
    @ApiModelProperty(value = "状态 0-正常 1-禁用", example = "1")
    private String status;

}
