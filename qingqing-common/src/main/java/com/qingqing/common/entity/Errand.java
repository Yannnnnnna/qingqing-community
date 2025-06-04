package com.qingqing.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 跑腿需求表
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Errand对象", description="跑腿需求表")
public class Errand implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "发布者ID")
    private Long userId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "酬劳")
    private BigDecimal reward;

    @ApiModelProperty(value = "状态(0-待接单,1-进行中,2-已完成,3-已取消)")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
