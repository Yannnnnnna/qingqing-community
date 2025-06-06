package com.qingqing.common.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 用户登录vo
 *
 * @author wyr on 2025/6/6
 */
@Data
@ApiModel("用户登录VO")
public class UserLoginVO {
    @ApiModelProperty(value = "用户ID", example = "1708892123456789012")
    private Long id;
    @ApiModelProperty(value = "token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxNzA4ODkyMTIzNDU2Nzg5MDEyIiwiaWF0IjoxNjg4OTYwMDAwLCJleHBpcmVkX3VzZXJfaWQiOjE3MDg4OTIxMjM0NTY3ODkwMTIsInVzZXJfbmFtZSI6ImFkbWluIn0.abc123xyz456")
    private String token;
}
