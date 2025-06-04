package com.qingqing.common.dto.admin.users;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息
 *
 * @author wyr on 2025/6/3
 */
@ApiModel("用户信息")
@Data
public class UsersDTO {
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID", example = "雪花id")
    private Long id;
    /**
     * 微信OpenID
     */
    @ApiModelProperty(value = "微信OpenID", example = "wx_open_id")
    private String openid;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "昵称", example = "aaa")
    private String nickname;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", example = "12345678901")
    private String phone;



    /**
     * 状态
     *
     */
    @ApiModelProperty(value = "状态", example = "1", notes = "1: 禁用, 0: 启用")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2025-05-28 19:27:47")
    private LocalDateTime createTime;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址", example = "重庆理工大学")
    private String address;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像URL", example = "https://example.com/avatar.jpg")
    private String avatar;
}
