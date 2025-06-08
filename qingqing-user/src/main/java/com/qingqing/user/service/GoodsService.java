package com.qingqing.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qingqing.common.dto.PageDTO;


import com.qingqing.common.dto.user.GoodsDTO;
import com.qingqing.common.dto.user.SecondHandGoodsPublishDTO;
import com.qingqing.common.dto.user.SecondHandGoodsUpdateDTO;
import com.qingqing.common.entity.Goods;
import com.qingqing.common.query.admin.GoodsPageQuery;
import com.qingqing.common.query.user.GoodsQuery;
import com.qingqing.common.vo.user.MySecondHandGoodsVO;
import com.qingqing.common.vo.user.SecondHandGoodsDetailVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 二手商品表 服务类
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
public interface GoodsService extends IService<Goods> {
    /**
     * 获取二手商品列表（条件+分页）
     * @param query 分页查询条件
     * @return
     */
    List<GoodsDTO> queryAll(GoodsQuery query);
    /** 根据ID查询单个二手商品详细信息
     * @param id 商品ID
     * @return GoodsDTO 商品详细信息
     */
    SecondHandGoodsDetailVO queryById(Long id);


    /**
     * 上传商品图片
     * @param file 商品图片文件
     * @param goods 商品名称或ID
     * @return 图片的存储路径或URL
     */
    String uploadFileToLocal(MultipartFile file, String goods);

    /**
     * 发布二手商品
     * @param goodsDTO
     */
    void publishSecondHandGoods(SecondHandGoodsPublishDTO goodsDTO, Long userId);

    /**
     * 查询当前用户发布的二手商品列表
     * @param userId 用户ID
     * @return 我的商品列表
     */
    List<MySecondHandGoodsVO> queryMyGoods(Long userId);

    /**
     * 获取我发布的所有商品
     * @param id
     * @return
     */
    List<GoodsDTO> queryMyAllGoods(Long id);

    /**
     * 更新二手商品信息
     * @param goodsDTO
     */
     void updateGoods(SecondHandGoodsUpdateDTO goodsDTO);
}
