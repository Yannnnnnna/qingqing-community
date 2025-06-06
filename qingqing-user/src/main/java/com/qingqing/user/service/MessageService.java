package com.qingqing.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingqing.common.dto.user.ImageSendDTO;
import com.qingqing.common.dto.user.MessageSendDTO;
import com.qingqing.common.entity.Message;
import com.qingqing.common.vo.user.MessageVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 消息服务接口
 */
public interface MessageService extends IService<Message> {

    /**
     * 获取指定商品的消息记录（分页）
     *
     * @param goodsId 商品ID
     * @param page    页码
     * @param size    每页大小
     * @return 消息列表
     */
    List<MessageVO> getMessagesByGoodsId(Long goodsId, Integer page, Integer size);

    /**
     * 获取指定商品和指定用户相关的所有消息记录（不分页）
     *
     * @param goodsId 商品ID
     * @param userId  用户ID
     * @return 消息列表
     */
    List<MessageVO> getMessagesByGoodsIdAndUserId(Long goodsId, Long userId);

    /**
     * 发送文本消息
     *
     * @param messageSendDTO 消息发送DTO
     * @return 发送的消息VO
     */
    MessageVO sendTextMessage(MessageSendDTO messageSendDTO);

    /**
     * 发送图片消息（原方法，直接上传文件）
     *
     * @param imageSendDTO 图片消息发送DTO
     * @return 发送的消息VO
     */
    MessageVO sendImageMessage(ImageSendDTO imageSendDTO);

    /**
     * 上传消息图片到本地
     *
     * @param file 图片文件
     * @return 图片URL
     */
    String uploadMessageImage(MultipartFile file);

    /**
     * 发送图片消息（使用已上传的图片URL）
     *
     * @param imageSendDTO 图片消息发送DTO（包含imageUrl）
     * @return 发送的消息VO
     */
    MessageVO sendImageMessageWithUrl(ImageSendDTO imageSendDTO);
}