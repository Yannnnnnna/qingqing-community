package com.qingqing.common.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户信息更新DTO")
public class UserProfileUpdateDTO {
    @ApiModelProperty(value = "用户ID", example = "1708892123456789012", required = true)
    private Long id;
    @ApiModelProperty(value = "微信OpenID", example = "oK-aB1c2D3e4F5g6H7i8J9k0M")
    private String openid;
    @ApiModelProperty(value = "手机号码", example = "13800000000")
    private String phone;
    @ApiModelProperty(value = "地址", example = "北京市海淀区中关村")
    private String address;

} 