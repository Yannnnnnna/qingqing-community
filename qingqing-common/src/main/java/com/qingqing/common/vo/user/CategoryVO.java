package com.qingqing.common.vo.user;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "商品类别")
public class CategoryVO {
    private String name;
} 