package com.qingqing.common.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;

/**
 * <p>
 * 描述：分页查询父类对象
 * </p>
 */
@Getter
@Setter
@ToString
public class PageQuery {
    @Min(value = 1, message = "页码最小值为1")
    @ApiModelProperty(value = "查询页码", example = "1",required = true)
    private long pageIndex;
    @Min(value = 1, message = "条数最小值为1")
    @ApiModelProperty(value = "查询条数", example = "10",required = true)
    private long pageSize;
}
