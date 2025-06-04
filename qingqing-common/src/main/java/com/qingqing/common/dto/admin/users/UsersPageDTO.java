package com.qingqing.common.dto.admin.users;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户信息分页查询结果
 *
 * @author wyr on 2025/6/3
 */
@ApiModel("用户信息分页查询结果")
@Data
public class UsersPageDTO {
    /**
     * 微信OpenID
     */
    @ApiModelProperty(value = "微信OpenID", example = "wx_open_id")
    private String openId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "昵称", example = "aaa")
    private String nickName;

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
}
