package com.qingqing.common.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户详细信息")
public class UserProfileVO {
    @ApiModelProperty(value = "用户ID", example = "1708892123456789012")
    private Long id;
    @ApiModelProperty(value = "微信OpenID", example = "oK-aB1c2D3e4F5g6H7i8J9k0L")
    private String openid;
    @ApiModelProperty(value = "用户名", example = "张三")
    private String nickname;
    @ApiModelProperty(value = "用户头像", example = "https://example.com/avatar.jpg")
    private String avatar;
    @ApiModelProperty(value = "手机号", example = "13800000000")
    private String phone;
    @ApiModelProperty(value = "密码", example = "123456")
    private String password;
    @ApiModelProperty(value = "地址", example = "北京市朝阳区大望路1号")
    private String address;


} 