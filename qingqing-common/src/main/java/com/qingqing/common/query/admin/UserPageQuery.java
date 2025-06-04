package com.qingqing.common.query.admin;

import com.qingqing.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户分页查询
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel("用户分页查询模型")
public class UserPageQuery extends PageQuery {
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
     * 状态（1: 禁用, 0: 启用）
     */
    @ApiModelProperty(value = "状态", example = "1", notes = "1: 禁用, 0: 启用")
    private Integer status;


}
