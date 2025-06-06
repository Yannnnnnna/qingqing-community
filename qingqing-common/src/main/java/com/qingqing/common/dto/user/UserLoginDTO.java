package com.qingqing.common.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * 用户登录信息
 */
@Data
@ApiModel(description = "用户登录信息")
public class UserLoginDTO {

    /**
     * 手机号（必填，11位数字）
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^\\d{11}$", message = "手机号必须为11位数字")
    @ApiModelProperty(value = "手机号", example = "13800138001", required = true)
    private String phone;

    /**
     * 密码（必填，6-20位字母或数字）
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须为6-20位")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "密码只能包含字母或数字")
    @ApiModelProperty(value = "密码", example = "123456", required = true)
    private String password;
}
