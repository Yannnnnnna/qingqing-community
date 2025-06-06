package com.qingqing.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qingqing.common.dto.admin.goods.GoodsDTO;
import com.qingqing.common.dto.admin.goods.GoodsPageDTO;
import com.qingqing.common.entity.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 二手商品表 Mapper 接口
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 分页查询二手商品，并关联查询发布者和类别信息
     * @param page 分页对象，由 Mybatis-Plus 自动处理分页逻辑
     * @param publisherName 发布者名字 (模糊查询)
     * @param categoryName 类别名 (模糊查询)
     * @param title 商品标题 (模糊查询)
     * @param status 商品状态
     * @return 分页结果的 IPage<GoodsPageDTO>
     */
    IPage<GoodsPageDTO> selectGoodsPage(IPage<Goods> page,
                                        @Param("publisherName") String publisherName,
                                        @Param("categoryName") String categoryName,
                                        @Param("title") String title,
                                        @Param("status") Integer status);

    /**
     * 根据商品ID查询商品详细信息（包含发布者名字和类别名）
     * @param id 商品ID
     * @return GoodsDTO
     */
    @Select("SELECT " +
            "g.id, " +
            "u.nickname as publisherName, " +
            "c.name as categoryName, " +
            "g.title, " +
            "g.price, " +
            "g.status, " +
            "g.images, " +
            "g.description, " +
            "g.create_time as createTime, " +
            "g.update_time as updateTime " +
            "FROM goods g " +
            "LEFT JOIN user u ON g.user_id = u.id " +
            "LEFT JOIN category c ON g.category_id = c.id " +
            "WHERE g.id = #{id}")
    GoodsDTO selectGoodsDTOById(@Param("id") Long id);
}
