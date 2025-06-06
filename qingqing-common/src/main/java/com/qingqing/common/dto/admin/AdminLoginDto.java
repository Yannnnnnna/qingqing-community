package com.qingqing.common.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
    @NotBlank(message = "用户名不能为空")
    @Size(max = 50, message = "用户名不能超过50个字符")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "用户名只能包含字母或数字")
    @ApiModelProperty(value = "用户名", example = "admin",required = true)
    private String userName;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(max = 100, message = "密码不能超过100个字符")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "密码只能包含字母或数字")
    @ApiModelProperty(value = "密码", example = "admin",required = true)
    private String password;

}
