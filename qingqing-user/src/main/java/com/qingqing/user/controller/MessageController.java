package com.qingqing.user.controller;


import com.qingqing.common.dto.user.ImageSendDTO;
import com.qingqing.common.dto.user.MessageSendDTO;
import com.qingqing.common.utils.BaseContext;
import com.qingqing.common.vo.JsonVO;
import com.qingqing.common.vo.user.MessageVO;
import com.qingqing.user.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 消息控制器
 * 处理商品买卖双方的消息功能
 */
@Api(tags = "消息管理")
@RestController
@RequestMapping("/user/message")
@Slf4j
@Validated
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 获取指定商品的所有消息记录（当前用户相关的）
     */
    @ApiOperation("获取商品消息记录")
    @GetMapping("/goods/{goodsId}")
    public JsonVO<List<MessageVO>> getMessagesByGoodsId(
            @ApiParam(value = "商品ID", required = true)
            @PathVariable @NotNull Long goodsId) {

        Long currentUserId = BaseContext.getCurrentId();
        log.info("获取商品{}的消息记录，当前用户ID：{}", goodsId, currentUserId);

        List<MessageVO> messages = messageService.getMessagesByGoodsIdAndUserId(goodsId, currentUserId);
        return JsonVO.success(messages);
    }

    /**
     * 发送文本消息
     */
    @ApiOperation("发送文本消息")
    @PostMapping("/send")
    public JsonVO<List<MessageVO>> sendMessage(
            @ApiParam(value = "消息发送请求", required = true)
            @RequestBody @Validated MessageSendDTO messageSendDTO) {

        Long currentUserId = BaseContext.getCurrentId();
        messageSendDTO.setSenderId(currentUserId);

        log.info("发送文本消息：发送者{}，接收者{}，商品ID：{}",
                currentUserId, messageSendDTO.getReceiverId(), messageSendDTO.getGoodsId());

        // 发送消息
        messageService.sendTextMessage(messageSendDTO);

        // 返回当前商品和当前用户的所有消息记录
        List<MessageVO> messages = messageService.getMessagesByGoodsIdAndUserId(
                messageSendDTO.getGoodsId(), currentUserId);

        return JsonVO.success(messages);
    }

    /**
     * 上传消息图片文件
     */
    @ApiOperation("上传消息图片文件")
    @PostMapping("/upload-image")
    public JsonVO<String> uploadMessageImage(
            @ApiParam(value = "图片文件", required = true)
            @RequestPart MultipartFile file) {

        try {
            String imageUrl = messageService.uploadMessageImage(file);
            return JsonVO.success(imageUrl, "消息图片上传成功");
        } catch (IllegalArgumentException e) {
            log.warn("消息图片上传请求参数异常：{}", e.getMessage());
            return JsonVO.fail(e.getMessage());
        } catch (Exception e) {
            log.error("消息图片上传过程中发生未知错误：{}", e.getMessage(), e);
            return JsonVO.fail("消息图片上传发生未知错误");
        }
    }

    /**
     * 发送图片消息
     */
    @ApiOperation("发送图片消息")
    @PostMapping("/send/image")
    public JsonVO<List<MessageVO>> sendImageMessage(@Validated @RequestBody ImageSendDTO imageSendDTO
            ) {

        Long currentUserId = BaseContext.getCurrentId();

        log.info("发送图片消息：发送者{}，接收者{}，商品ID：{}", currentUserId, imageSendDTO.getSenderId(), imageSendDTO.getGoodsId());

        imageSendDTO.setSenderId(currentUserId);


        // 发送图片消息
        messageService.sendImageMessageWithUrl(imageSendDTO);

        // 返回当前商品和当前用户的所有消息记录
        List<MessageVO> messages = messageService.getMessagesByGoodsIdAndUserId(imageSendDTO.getGoodsId(), currentUserId);

        return JsonVO.success(messages);
    }
}