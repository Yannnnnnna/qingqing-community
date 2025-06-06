package com.qingqing.common.query.admin;

import com.qingqing.common.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*; // 确保导入所有需要的注解

/**
 * 用户分页查询
 *
 * @author wyr on 2025/6/3
 */
@Data
@ApiModel("用户分页查询模型")
public class UserPageQuery extends PageQuery {

    /**
     * 微信OpenID（可选，最大长度50）
     */
    @Size(max = 50, message = "微信OpenID不能超过50个字符")
    @ApiModelProperty(value = "微信OpenID", example = "wx_open_id")
    private String openid;

    /**
     * 昵称（可选，最大长度50）
     */
    @Size(max = 50, message = "昵称不能超过50个字符")
    @ApiModelProperty(value = "昵称", example = "aaa")
    private String nickname;

    /**
     * 手机号（可选，必须为11位数字）
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "请输入有效的手机号")
    @ApiModelProperty(value = "手机号", example = "12345678901")
    private String phone;

    /**
     * 状态（可选，只能为0或1）
     */
    // 使用 @Min 和 @Max 校验 Integer 类型的值
    @Min(value = 0, message = "状态必须为0或1")
    @Max(value = 1, message = "状态必须为0或1")
    @ApiModelProperty(value = "状态", example = "1", notes = "1: 禁用, 0: 启用")
    private Integer status;
}