package com.qingqing.user.controller;




import com.qingqing.common.dto.user.GoodsDTO;
import com.qingqing.common.dto.user.SecondHandGoodsPublishDTO;
import com.qingqing.common.dto.user.SecondHandGoodsUpdateDTO;
import com.qingqing.common.query.user.GoodsQuery;
import com.qingqing.common.utils.BaseContext;
import com.qingqing.common.vo.JsonVO;
import com.qingqing.common.vo.user.SecondHandGoodsDetailVO;
import com.qingqing.user.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * <p>
 * 二手商品表 前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@Slf4j
@RestController
@RequestMapping("/user/second-hand-goods")
@Api(tags = "二手商品管理")
public class GoodsController {
    @Autowired
    GoodsService goodsService;
    /**
     * 获取二手商品列表(条件)
     * @param
     * @return JsonVO<PageDTO<GoodsPageDTO>> 查询结果
     */
    @ApiOperation("获取二手商品列表（条件）")
    @GetMapping("/query-all")
    public JsonVO<List<GoodsDTO>> queryAll(@Validated GoodsQuery goodsQuery){
        List<GoodsDTO> list = goodsService.queryAll(goodsQuery);
        if (list == null || list.isEmpty()) {
            return JsonVO.fail("查询失败，没有符合条件的商品");
        }
        return JsonVO.success(list, "商品列表查询成功");

    }
    /**
     * 获取单个二手商品详细
     */
    @ApiOperation("获取单个二手商品详细")
    @GetMapping("/detail/{id}")
    public JsonVO<SecondHandGoodsDetailVO> query(@PathVariable("id") Long id){
        if(id == null || id <= 0) {
            return JsonVO.fail("商品ID无效");
        }
        SecondHandGoodsDetailVO goodsDetailVO = goodsService.queryById(id);
        if (goodsDetailVO == null) {
            return JsonVO.fail("商品不存在");
        }
        return JsonVO.success(goodsDetailVO, "商品查询成功");
    }

    /**
     * 获取当前用户的二手商品列表
     * @return
     */
    @ApiOperation("获取当前用户的二手商品列表")
    @GetMapping("/my-goods")
    public JsonVO<List<GoodsDTO>> queryMy(){
       Long id = BaseContext.getCurrentId();
        log.info("查询当前用户ID：{} 的二手商品列表", id);
        List<GoodsDTO> list = goodsService.queryMyAllGoods(id);
        if (list == null || list.isEmpty()) {
            return JsonVO.fail("查询失败，没有符合条件的商品");
        }
        return JsonVO.success(list, "商品列表查询成功");
    }
    /**
     * 发布二手商品
     *
     */
    @ApiOperation("发布二手商品")
    @PostMapping("/publish")
    public JsonVO<String> publish(@Validated @RequestBody SecondHandGoodsPublishDTO goodsDTO){

        try {
            // publishDTO.getImageUrls() 此时已经是前端传来的本地可访问URL列表
            Long userId = BaseContext.getCurrentId(); // 设置当前用户ID
            goodsService.publishSecondHandGoods(goodsDTO, userId); // 这个方法现在只负责业务逻辑和数据库保存
            return JsonVO.success("商品发布成功");
        } catch (Exception e) {
            log.error("商品发布失败：{}", e.getMessage(), e);
            return JsonVO.fail("商品发布失败，请稍后再试");
        }
    }


    /**
     * 上传单个商品图片文件
     * @param file 商品图片文件 (前端直接发送的文件)
     * @return 上传成功后的图片URL
     */
    @ApiOperation("上传单个商品图片文件")
    @PostMapping("/upload-image-file") // 一个新的、专门用于文件上传的接口
    public JsonVO<String> uploadGoodsImageFile(@RequestPart MultipartFile file) {
        try {
            // 调用服务层处理文件保存，并返回可访问的URL
            // "goods" 表示图片会保存在 G:\qingqing-community-imgs\goods\ 子目录下
            String imageUrl = goodsService.uploadFileToLocal(file, "goods");
            return JsonVO.success(imageUrl, "商品图片上传成功");
        } catch (IllegalArgumentException e) {
            log.warn("商品图片上传请求参数异常：{}", e.getMessage());
            return JsonVO.fail(e.getMessage());
        } catch (Exception e) {
            log.error("商品图片上传过程中发生未知错误：{}", e.getMessage(), e);
            return JsonVO.fail("商品图片上传发生未知错误");
        }
    }

    @ApiOperation("编辑二手商品信息")
    @PutMapping("/edit")
    public JsonVO<String> edit(@Validated @RequestBody SecondHandGoodsUpdateDTO goodsDTO){
        goodsService.updateGoods(goodsDTO);
        return JsonVO.success("商品修改成功");
    }

}
