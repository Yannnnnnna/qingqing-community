package com.qingqing.common.query.user;

 import com.qingqing.common.query.PageQuery;
 import io.swagger.annotations.ApiModel;
 import io.swagger.annotations.ApiModelProperty;
 import lombok.Data;

 import javax.validation.constraints.Size;

 /**
  * 商品分页查询
  */
 @Data
 @ApiModel(description = "商品查询条件")
 public class GoodsQuery {

     @ApiModelProperty(value = "发布者名字", example = "张三")
     @Size(max = 50, message = "发布者名字长度不能超过50个字符")
     private String publisherName;

     @ApiModelProperty(value = "类别名", example = "家用电器")
     @Size(max = 50, message = "类别名长度不能超过50个字符")
     private String categoryName;

     @ApiModelProperty(value = "商品标题", example = "Java编程思想")
     @Size(max = 100, message = "商品标题长度不能超过100个字符")
     private String title;
 }