package com.qingqing.common.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "用户登录信息")
public class UserLoginDTO {
    @ApiModelProperty(value = "手机号", example = "13800138001")
    private String phone;
    @ApiModelProperty(value = "密码", example = "123456")
    private String password;
} 