package com.qingqing.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingqing.common.dto.user.GoodsDTO;
import com.qingqing.common.dto.user.SecondHandGoodsPublishDTO;
import com.qingqing.common.entity.Category;
import com.qingqing.common.entity.Goods;
import com.qingqing.common.entity.User;
import com.qingqing.common.query.user.GoodsQuery;
import com.qingqing.common.utils.JsonUtils;
import com.qingqing.common.vo.user.MySecondHandGoodsVO;
import com.qingqing.common.vo.user.SecondHandGoodsDetailVO;
import com.qingqing.user.mapper.CategoryMapper;
import com.qingqing.user.mapper.GoodsMapper;
import com.qingqing.user.mapper.UserMapper;
import com.qingqing.user.service.GoodsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Objects;

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
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private UserMapper userMapper; // 注入 UserMapper 来查询用户信息

    @Value("${app.upload.base-path}")
    private String uploadBasePath; // G:/qingqing-community-imgs/

    @Value("${app.upload.web-prefix}")
    private String webPrefix;     // /imgs/

    /**
     * 获取二手商品列表 不分页
     *
     * @param
     * @return
     */
    @Override
    public List<GoodsDTO> queryAll(GoodsQuery query) {
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();

        // 1. 根据商品标题模糊查询
        if (query.getTitle() != null && !query.getTitle().isEmpty()) {
            wrapper.like(Goods::getTitle, query.getTitle());
        }

        // 2. 根据类别名查询 categoryId
        Long categoryId = null;
        if (query.getCategoryName() != null && !query.getCategoryName().isEmpty()) {
            Category category = categoryMapper.selectOne(
                    new LambdaQueryWrapper<Category>().eq(Category::getName, query.getCategoryName())
            );
            if (category != null) {
                categoryId = category.getId();
                wrapper.eq(Goods::getCategoryId, categoryId);
            } else {
                // 如果类别名不存在，则不可能查询到任何商品，可以直接返回空列表
                return Collections.emptyList();
            }
        }

        // 3. 根据发布者名字查询 userId
        Long userId = null;
        if (query.getPublisherName() != null && !query.getPublisherName().isEmpty()) {
            User user = userMapper.selectOne(
                    new LambdaQueryWrapper<User>().eq(User::getNickname, query.getPublisherName()) // 假设用户表有name字段
            );
            if (user != null) {
                userId = user.getId();
                wrapper.eq(Goods::getUserId, userId);
            } else {
                // 如果发布者名字不存在，则不可能查询到任何商品，可以直接返回空列表
                return Collections.emptyList();
            }
        }

        // 4. 只查询已上架的商品 (status = 1)
        wrapper.eq(Goods::getStatus, 1);

        // 5. 执行查询，获取 Goods 实体列表
        List<Goods> goodsList = goodsMapper.selectList(wrapper);

        // 6. 将 Goods 实体列表转换为 GoodsDTO 列表
        return convertToGoodsDTOList(goodsList);
    }

    /**
     * 将 Goods 实体列表转换为 GoodsDTO 列表的辅助方法
     */
    private List<GoodsDTO> convertToGoodsDTOList(List<Goods> goodsList) {
        if (goodsList == null || goodsList.isEmpty()) {
            return Collections.emptyList();
        }

        // 收集所有需要查询的用户ID，避免在循环中频繁查询数据库
        List<Long> userIds = goodsList.stream()
                .map(Goods::getUserId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        // 批量查询用户信息
        Map<Long, User> userMap;
        if (!userIds.isEmpty()) {
            List<User> userList = userMapper.selectBatchIds(userIds);
            userMap = userList.stream()
                    .collect(Collectors.toMap(User::getId, Function.identity()));
        } else {
            userMap = Collections.emptyMap();
        }

        return goodsList.stream().map(goods -> {
            GoodsDTO dto = new GoodsDTO();
            BeanUtils.copyProperties(goods, dto);
            dto.setId(goods.getId());
            dto.setTitle(goods.getTitle());

            // 设置发布者名字
            User publisher = userMap.get(goods.getUserId());
            if (publisher != null) {
                dto.setPublisherName(publisher.getNickname());
            } else {
                dto.setPublisherName("未知");
            }

            // *** 修改1: 使用JsonUtils工具类处理图片JSON转换 ***
            String imagesJson = goods.getImages();
            System.out.println("商品ID: " + goods.getId() + ", images JSON: " + imagesJson);

            // 使用工具类直接获取第一张图片
            String firstImage = JsonUtils.getFirstImageFromJson(imagesJson);
            dto.setImage(firstImage);

            if (firstImage != null) {
                System.out.println("成功获取第一张图片: " + firstImage);
            } else {
                System.out.println("没有图片或解析失败");
            }

            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * 根据ID查询单个二手商品详细信息
     *
     * @param id 商品ID
     * @return SecondHandGoodsDetailVO 商品详细信息
     */
    @Override
    public SecondHandGoodsDetailVO queryById(Long id) {
        // 2. 根据ID查询商品信息
        Goods goods = goodsMapper.selectById(id);

        // 3. 判断商品是否存在
        if (goods == null) {
            throw new RuntimeException("商品不存在，ID: " + id);
        }

        // 4. 检查商品状态，只返回已上架的商品
        if (goods.getStatus() != 1) {
            throw new RuntimeException("商品未上架或已下架");
        }

        // 5. 转换为VO对象
        SecondHandGoodsDetailVO vo = new SecondHandGoodsDetailVO();

        // 6. 设置基本信息
        vo.setId(goods.getId());
        vo.setTitle(goods.getTitle());
        vo.setDescription(goods.getDescription());
        vo.setPrice(goods.getPrice());

        // *** 修改2: 使用JsonUtils工具类处理图片列表 ***
        if (goods.getImages() != null && !goods.getImages().isEmpty()) {
            List<String> imageUrls = JsonUtils.parseJsonToStringList(goods.getImages());
            vo.setImageUrls(imageUrls);
        } else {
            vo.setImageUrls(Collections.emptyList());
        }

        return vo;
    }

    /**
     * 通用的文件上传到本地方法
     *
     * @param file             要上传的 MultipartFile 文件
     * @param subDirectoryName 文件要保存的子目录名（例如 "avatars", "goods"）
     * @return 文件在服务器上的可访问URL
     */
    public String uploadFileToLocal(MultipartFile file, String subDirectoryName) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }

        // 确保子目录存在 (G:\qingqing-community-imgs\goods\)
        File targetDir = new File(uploadBasePath, subDirectoryName + File.separator);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFileName = UUID.randomUUID().toString() + fileExtension;

        // 完整的文件本地路径 (G:\qingqing-community-imgs\goods\,uuid.jpg)
        Path filePath = Paths.get(targetDir.getAbsolutePath(), newFileName);

        // 保存文件到本地文件系统
        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 生成前端可访问的URL (http://localhost:8080/imgs/goods/uuid.jpg)
        return webPrefix + subDirectoryName + "/" + newFileName;
    }

    /**
     * 处理商品发布逻辑
     *
     * @param publishDTO 前端提交的商品发布DTO，其中 imageUrls 已经是前端上传后返回的URL列表
     * @return
     */
    public void publishSecondHandGoods(SecondHandGoodsPublishDTO publishDTO) {
        // 这里不再需要下载图片，因为图片已经通过单独的上传接口保存了
        Goods goods = new Goods();

        // 2. 设置发布者ID
        goods.setUserId(publishDTO.getPublisherId()); // DTO的publisherId 对应 Goods的 userId

        // 3. 根据 categoryName 获取 categoryId
        // 这里需要根据你的实际业务逻辑和 CategoryMapper 来查询类别ID
        // 假设 CategoryMapper 提供一个根据名称查询ID的方法
        Category category = categoryMapper.selectOne(new QueryWrapper<Category>().eq("name", publishDTO.getCategoryName()));
        Long categoryId = category.getId();
        if (categoryId == null) {
            throw new RuntimeException("商品分类不存在: " + publishDTO.getCategoryName());
        }
        goods.setCategoryId(categoryId);

        // 4. 设置其他商品信息
        goods.setTitle(publishDTO.getTitle());
        goods.setDescription(publishDTO.getDescription());
        goods.setPrice(publishDTO.getPrice());

        // *** 修改3: 使用JsonUtils工具类处理图片URL列表转JSON ***
        // 直接使用前端提交的 imageUrls 列表，它们已经是本地服务器的URL
        String imagesJson = JsonUtils.convertStringListToJson(publishDTO.getImageUrls());
        goods.setImages(imagesJson);

        // 6. 设置初始状态 (通常为0-待审核)
        goods.setStatus(0); // 0-待审核
        // 8. 插入数据库
        int rows = goodsMapper.insert(goods); // 使用 goodsMapper 插入
        if (rows == 0) {
            throw new RuntimeException("商品发布失败，请稍后再试");
        }
    }

    /**
     * 查询当前用户发布的二手商品列表
     *
     * @param userId 用户ID
     * @return 我的商品列表
     */
    @Override
    public List<MySecondHandGoodsVO> queryMyGoods(Long userId) {
        // 1. 参数校验
        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        // 2. 查询该用户发布的所有商品
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Goods::getUserId, userId)
                .orderByDesc(Goods::getCreateTime); // 按创建时间倒序排列

        List<Goods> goodsList = goodsMapper.selectList(wrapper);

        // 3. 转换为VO对象列表
        return goodsList.stream().map(goods -> {
            MySecondHandGoodsVO vo = new MySecondHandGoodsVO();

            // 设置基本信息
            vo.setId(goods.getId());
            vo.setTitle(goods.getTitle());
            vo.setPrice(goods.getPrice());
            vo.setStatus(goods.getStatus());

            // 设置状态描述
            vo.setStatusDesc(getStatusDescription(goods.getStatus()));

            // *** 修改4: 使用JsonUtils工具类获取封面图片 ***
            String coverImage = JsonUtils.getFirstImageFromJson(goods.getImages());
            vo.setCoverImage(coverImage);

            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 根据状态码获取状态描述
     *
     * @param status 状态码
     * @return 状态描述
     */
    private String getStatusDescription(Integer status) {
        if (status == null) {
            return "未知状态";
        }

        switch (status) {
            case 0:
                return "待审核";
            case 1:
                return "已上架";
            case 2:
                return "已下架";
            case 3:
                return "已售出";
            default:
                return "未知状态";
        }
    }
}