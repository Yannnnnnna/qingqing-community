package com.qingqing.admin.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingqing.admin.mapper.GoodsMapper;
import com.qingqing.admin.service.GoodsService;
import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.goods.GoodsDTO;
import com.qingqing.common.dto.admin.goods.GoodsPageDTO;
import com.qingqing.common.dto.admin.goods.UpdateGoodsStatusDTO;
import com.qingqing.common.entity.Goods;
import com.qingqing.common.exception.BaseException;
import com.qingqing.common.query.admin.GoodsPageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 二手商品表 服务实现类
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 获取二手商品列表（条件+分页）
     * @param goodsPageQuery
     * @return
     */
    @Override
    public PageDTO<GoodsPageDTO> queryAll(GoodsPageQuery goodsPageQuery) {
        // 1. 创建 Mybatis-Plus 的分页对象
        // goodsPageQuery.getPageNum() 是当前页码 (从1开始)
        // goodsPageQuery.getPageSize() 是每页记录数
        Page<Goods> page = new Page<>(goodsPageQuery.getPageIndex(), goodsPageQuery.getPageSize());

        // 2. 调用 GoodsMapper 中定义的联表分页查询方法
        IPage<GoodsPageDTO> goodsPage = goodsMapper.selectGoodsPage(
                page,
                goodsPageQuery.getPublisherName(),
                goodsPageQuery.getCategoryName(),
                goodsPageQuery.getTitle(),
                goodsPageQuery.getStatus()
        );
        if(goodsPage == null||  goodsPage.getRecords().isEmpty()){
            throw  new BaseException("没有查询到数据");
        }
        // 3. 将 Mybatis-Plus 的 IPage 结果转换为自定义的 PageDTO
        PageDTO<GoodsPageDTO> resultPageDTO = new PageDTO<>();
        resultPageDTO.setRows(goodsPage.getRecords()); // 设置查询到的数据列表
        resultPageDTO.setTotal(goodsPage.getTotal());     // 设置总记录数
        resultPageDTO.setPageIndex(goodsPage.getCurrent()); // 设置当前页码
        resultPageDTO.setPageSize(goodsPage.getSize());       // 设置每页记录数

        return resultPageDTO;
    }
    /**
     * 根据ID查询单个二手商品详细信息
     * @param id 商品ID
     * @return GoodsDTO 商品详细信息
     */
    @Override
    public GoodsDTO queryById(Long id) {
        // 参数校验
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("商品ID不能为空或小于等于0");
        }

        // 查询商品详情
        GoodsDTO goodsDTO = goodsMapper.selectGoodsDTOById(id);

        if (goodsDTO == null) {
            throw new RuntimeException("商品不存在，ID: " + id);
        }

        // 将JSON字符串转换为图片URL列表
        goodsDTO.parseImagesFromJson();

        return goodsDTO;
    }

    /**
     * 下架或审核二手商品
     * @param updateStatusDTO
     * @return
     */
    @Override
    public boolean updateGoodsStatus(UpdateGoodsStatusDTO updateStatusDTO) {
        Goods goods = new Goods();
        goods.setId(updateStatusDTO.getId());
        goods.setStatus(updateStatusDTO.getStatus());
        // 调用 Mybatis-Plus 的 updateById 方法更新商品状态
        int rows = goodsMapper.updateById(goods);
        return rows > 0; // 更新成功
    }

}
