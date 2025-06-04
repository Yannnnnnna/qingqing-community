package com.qingqing.admin.controller;


import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.users.UpdateUsersStatusDTO;
import com.qingqing.common.dto.admin.users.UsersDTO;
import com.qingqing.common.dto.admin.users.UsersPageDTO;
import com.qingqing.common.query.admin.UserPageQuery;
import com.qingqing.common.vo.JsonVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author anonymous
 * @since 2025-05-28
 */
@RestController
@RequestMapping("/users")
@Api(tags = "用户管理")
public class UserController {
    /**
     * 获取用户列表（条件+分页）
     */
    @GetMapping("/query-all")
    @ApiOperation("获取用户列表（条件+分页）")
    public JsonVO<PageDTO<UsersPageDTO>> queryAll(UserPageQuery query) {
        return null;
    }

    /**
     * 获取用户详细
     */
    @GetMapping("/query/{id}")
    @ApiOperation("获取用户详细")
    public JsonVO<UsersDTO> query(@PathVariable("id") Long id) {
        return null;
    }
    /**
     * 修改状态
     */
    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    public JsonVO<String> updateStatus(@RequestBody UpdateUsersStatusDTO dto) {
        return null;
    }
}
