package com.qingqing.common.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@ApiModel(value = "用户注册信息")
public class UserRegisterDTO {

    @ApiModelProperty(value = "用户名", example = "张三")
    @NotBlank(message = "用户名不能为空")
    @Size(max = 20, message = "用户名长度不能超过20个字符")
    private String nickname;

    @ApiModelProperty(value = "手机号", example = "13800000000")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @ApiModelProperty(value = "密码", example = "123456")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6到20个字符之间")
    private String password;
}