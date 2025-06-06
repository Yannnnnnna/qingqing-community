package com.qingqing.common.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户注册信息")
public class UserRegisterDTO {

    @ApiModelProperty(value = "用户名", example = "张三")
    private String nickname;
    @ApiModelProperty(value = "手机号", example = "13800000000")
    private String phone;
    @ApiModelProperty(value = "密码", example = "123456")
    private String password;


} 