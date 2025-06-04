package com.qingqing.common.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 管理员登录dto
 *
 * @author wyr on 2025/6/2
 */
 @Data
 @ApiModel("管理员登录数据传输模型")
public class AdminLoginDto {
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", example = "admin",required = true)
    private String userName;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", example = "admin",required = true)
    private String password;

}
