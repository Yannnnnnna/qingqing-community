package com.qingqing.common.dto.user;

    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;

    import javax.validation.constraints.NotNull;
    import javax.validation.constraints.Pattern;
    import javax.validation.constraints.Size;

    @Data
    @ApiModel(value = "用户信息更新DTO")
    public class UserProfileUpdateDTO {

        @ApiModelProperty(value = "用户ID", example = "1708892123456789012", required = true)
        @NotNull(message = "用户ID不能为空")
        private Long id;

        @ApiModelProperty(value = "微信OpenID", example = "oK-aB1c2D3e4F5g6H7i8J9k0M")
        @Size(max = 32, message = "微信OpenID长度不能超过32个字符")
        private String openid;

        @ApiModelProperty(value = "手机号码", example = "13800000000")
        @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号码格式不正确")
        private String phone;

        @ApiModelProperty(value = "地址", example = "北京市海淀区中关村")
        @Size(max = 100, message = "地址长度不能超过100个字符")
        private String address;
    }