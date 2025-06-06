package com.qingqing.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingqing.common.dto.user.ImageSendDTO;
import com.qingqing.common.dto.user.MessageSendDTO;
import com.qingqing.common.entity.Goods;
import com.qingqing.common.entity.Message;
import com.qingqing.common.entity.User;
import com.qingqing.common.vo.user.MessageVO;
import com.qingqing.user.mapper.GoodsMapper;
import com.qingqing.user.mapper.MessageMapper;
import com.qingqing.user.mapper.UserMapper;
import com.qingqing.user.service.GoodsService;
import com.qingqing.user.service.MessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 消息服务实现类
 *
 * @author wyr on 2025/6/6
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsService goodsService;

    @Override
    public List<MessageVO> getMessagesByGoodsId(Long goodsId, Integer page, Integer size) {
        // 1. 参数校验
        if (goodsId == null) {
            throw new IllegalArgumentException("商品ID不能为空");
        }

        // 2. 构建分页查询条件
        Page<Message> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getGoodsId, goodsId)
                .orderByAsc(Message::getCreateTime); // 按时间正序排列，聊天记录从早到晚

        // 3. 执行分页查询
        Page<Message> messagePages = messageMapper.selectPage(pageParam, wrapper);
        List<Message> messageList = messagePages.getRecords();

        if (messageList == null || messageList.isEmpty()) {
            return Collections.emptyList();
        }

        return convertToMessageVOList(messageList, goodsId);
    }

    /**
     * 获取指定商品和指定用户相关的所有消息记录（不分页）
     */
    @Override
    public List<MessageVO> getMessagesByGoodsIdAndUserId(Long goodsId, Long userId) {
        // 1. 参数校验
        if (goodsId == null) {
            throw new IllegalArgumentException("商品ID不能为空");
        }
        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }

        // 2. 构建查询条件：查询该商品下当前用户参与的所有消息
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getGoodsId, goodsId)
                .and(w -> w.eq(Message::getSenderId, userId).or().eq(Message::getReceiverId, userId))
                .orderByAsc(Message::getCreateTime); // 按时间正序排列

        // 3. 执行查询
        List<Message> messageList = messageMapper.selectList(wrapper);

        if (messageList == null || messageList.isEmpty()) {
            return Collections.emptyList();
        }

        return convertToMessageVOList(messageList, goodsId);
    }

    /**
     * 将消息实体列表转换为VO列表的通用方法
     */
    private List<MessageVO> convertToMessageVOList(List<Message> messageList, Long goodsId) {
        // 4. 批量查询用户信息，避免N+1查询问题
        List<Long> userIds = messageList.stream()
                .flatMap(msg -> List.of(msg.getSenderId(), msg.getReceiverId()).stream())
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        Map<Long, User> userMap = Collections.emptyMap();
        if (!userIds.isEmpty()) {
            List<User> userList = userMapper.selectBatchIds(userIds);
            userMap = userList.stream()
                    .collect(Collectors.toMap(User::getId, Function.identity()));
        }

        // 5. 查询商品信息
        Goods goods = goodsMapper.selectById(goodsId);
        String goodsTitle = goods != null ? goods.getTitle() : "未知商品";

        // 6. 转换为VO对象
        final Map<Long, User> finalUserMap = userMap;
        return messageList.stream().map(message -> {
            MessageVO vo = new MessageVO();
            BeanUtils.copyProperties(message, vo);

            // 设置商品信息
            vo.setGoodsTitle(goodsTitle);

            // 设置发送者信息
            User sender = finalUserMap.get(message.getSenderId());
            if (sender != null) {
                vo.setSenderNickname(sender.getNickname());
            } else {
                vo.setSenderNickname("未知用户");
            }

            // 设置接收者信息
            User receiver = finalUserMap.get(message.getReceiverId());
            if (receiver != null) {
                vo.setReceiverNickname(receiver.getNickname());
            } else {
                vo.setReceiverNickname("未知用户");
            }

            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public MessageVO sendTextMessage(MessageSendDTO messageSendDTO) {
        // 1. 参数校验
        if (messageSendDTO == null) {
            throw new IllegalArgumentException("消息发送请求不能为空");
        }
        if (messageSendDTO.getGoodsId() == null) {
            throw new IllegalArgumentException("商品ID不能为空");
        }
        if (messageSendDTO.getSenderId() == null) {
            throw new IllegalArgumentException("发送者ID不能为空");
        }
        if (messageSendDTO.getReceiverId() == null) {
            throw new IllegalArgumentException("接收者ID不能为空");
        }
        if (messageSendDTO.getContent() == null || messageSendDTO.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("消息内容不能为空");
        }

        // 2. 验证商品是否存在
        Goods goods = goodsMapper.selectById(messageSendDTO.getGoodsId());
        if (goods == null) {
            throw new RuntimeException("商品不存在");
        }

        // 3. 验证用户是否存在
        User sender = userMapper.selectById(messageSendDTO.getSenderId());
        User receiver = userMapper.selectById(messageSendDTO.getReceiverId());
        if (sender == null) {
            throw new RuntimeException("发送者不存在");
        }
        if (receiver == null) {
            throw new RuntimeException("接收者不存在");
        }

        // 4. 创建消息实体
        Message message = new Message();
        message.setGoodsId(messageSendDTO.getGoodsId());
        message.setSenderId(messageSendDTO.getSenderId());
        message.setReceiverId(messageSendDTO.getReceiverId());
        message.setContentType(0); // 0-文本消息
        message.setContent(messageSendDTO.getContent().trim());
        message.setCreateTime(LocalDateTime.now());

        // 5. 保存到数据库
        int result = messageMapper.insert(message);
        if (result <= 0) {
            throw new RuntimeException("消息发送失败");
        }

        // 6. 构建返回的VO对象
        MessageVO vo = new MessageVO();
        BeanUtils.copyProperties(message, vo);
        vo.setGoodsTitle(goods.getTitle());
        vo.setSenderNickname(sender.getNickname());
        vo.setReceiverNickname(receiver.getNickname());

        return vo;
    }

    /**
     * 上传消息图片到本地
     */
    @Override
    public String uploadMessageImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("图片文件不能为空");
        }

        // 使用商品服务的文件上传方法，将图片保存到 "messages" 子目录
        return goodsService.uploadFileToLocal(file, "messages");
    }

    /**
     * 发送图片消息（使用已上传的图片URL）
     */
    @Override
    public MessageVO sendImageMessageWithUrl(ImageSendDTO imageSendDTO) {
        // 1. 参数校验
        if (imageSendDTO == null) {
            throw new IllegalArgumentException("图片消息发送请求不能为空");
        }
        if (imageSendDTO.getGoodsId() == null) {
            throw new IllegalArgumentException("商品ID不能为空");
        }
        if (imageSendDTO.getSenderId() == null) {
            throw new IllegalArgumentException("发送者ID不能为空");
        }
        if (imageSendDTO.getReceiverId() == null) {
            throw new IllegalArgumentException("接收者ID不能为空");
        }
        if (imageSendDTO.getImageUrl() == null || imageSendDTO.getImageUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("图片URL不能为空");
        }

        // 2. 验证商品是否存在
        Goods goods = goodsMapper.selectById(imageSendDTO.getGoodsId());
        if (goods == null) {
            throw new RuntimeException("商品不存在");
        }

        // 3. 验证用户是否存在
        User sender = userMapper.selectById(imageSendDTO.getSenderId());
        User receiver = userMapper.selectById(imageSendDTO.getReceiverId());
        if (sender == null) {
            throw new RuntimeException("发送者不存在");
        }
        if (receiver == null) {
            throw new RuntimeException("接收者不存在");
        }

        // 4. 创建消息实体
        Message message = new Message();
        message.setGoodsId(imageSendDTO.getGoodsId());
        message.setSenderId(imageSendDTO.getSenderId());
        message.setReceiverId(imageSendDTO.getReceiverId());
        message.setContentType(1); // 1-图片消息
        message.setContent(imageSendDTO.getImageUrl().trim()); // 图片URL作为消息内容
        message.setCreateTime(LocalDateTime.now());

        // 5. 保存到数据库
        int result = messageMapper.insert(message);
        if (result <= 0) {
            throw new RuntimeException("图片消息发送失败");
        }

        // 6. 构建返回的VO对象
        MessageVO vo = new MessageVO();
        BeanUtils.copyProperties(message, vo);
        vo.setGoodsTitle(goods.getTitle());
        vo.setSenderNickname(sender.getNickname());
        vo.setReceiverNickname(receiver.getNickname());

        return vo;
    }

    @Override
    public MessageVO sendImageMessage(ImageSendDTO imageSendDTO) {
        // 1. 参数校验
        if (imageSendDTO == null) {
            throw new IllegalArgumentException("图片消息发送请求不能为空");
        }
        if (imageSendDTO.getGoodsId() == null) {
            throw new IllegalArgumentException("商品ID不能为空");
        }
        if (imageSendDTO.getSenderId() == null) {
            throw new IllegalArgumentException("发送者ID不能为空");
        }
        if (imageSendDTO.getReceiverId() == null) {
            throw new IllegalArgumentException("接收者ID不能为空");
        }
        if (imageSendDTO.getFile() == null || imageSendDTO.getFile().isEmpty()) {
            throw new IllegalArgumentException("图片文件不能为空");
        }

        // 2. 验证商品是否存在
        Goods goods = goodsMapper.selectById(imageSendDTO.getGoodsId());
        if (goods == null) {
            throw new RuntimeException("商品不存在");
        }

        // 3. 验证用户是否存在
        User sender = userMapper.selectById(imageSendDTO.getSenderId());
        User receiver = userMapper.selectById(imageSendDTO.getReceiverId());
        if (sender == null) {
            throw new RuntimeException("发送者不存在");
        }
        if (receiver == null) {
            throw new RuntimeException("接收者不存在");
        }

        // 4. 上传图片文件，获取图片URL
        String imageUrl;
        try {
            // 使用商品服务的文件上传方法，将图片保存到 "messages" 子目录
            imageUrl = goodsService.uploadFileToLocal(imageSendDTO.getFile(), "messages");
        } catch (Exception e) {
            throw new RuntimeException("图片上传失败: " + e.getMessage());
        }

        // 5. 创建消息实体
        Message message = new Message();
        message.setGoodsId(imageSendDTO.getGoodsId());
        message.setSenderId(imageSendDTO.getSenderId());
        message.setReceiverId(imageSendDTO.getReceiverId());
        message.setContentType(1); // 1-图片消息
        message.setContent(imageUrl); // 图片URL作为消息内容
        message.setCreateTime(LocalDateTime.now());

        // 6. 保存到数据库
        int result = messageMapper.insert(message);
        if (result <= 0) {
            throw new RuntimeException("图片消息发送失败");
        }

        // 7. 构建返回的VO对象
        MessageVO vo = new MessageVO();
        BeanUtils.copyProperties(message, vo);
        vo.setGoodsTitle(goods.getTitle());
        vo.setSenderNickname(sender.getNickname());
        vo.setReceiverNickname(receiver.getNickname());

        return vo;
    }
}