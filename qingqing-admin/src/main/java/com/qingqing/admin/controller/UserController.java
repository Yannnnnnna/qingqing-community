package com.qingqing.admin.controller;


import com.qingqing.admin.service.AdminService;
import com.qingqing.admin.service.UserService;
import com.qingqing.common.dto.PageDTO;
import com.qingqing.common.dto.admin.AdminDTO;
import com.qingqing.common.dto.admin.users.UpdateUsersStatusDTO;
import com.qingqing.common.dto.admin.users.UsersDTO;
import com.qingqing.common.dto.admin.users.UsersPageDTO;
import com.qingqing.common.query.admin.UserPageQuery;
import com.qingqing.common.vo.JsonVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
@RequestMapping("/admin/users")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 获取用户列表（条件+分页）
     */
    @GetMapping("/query-all")
    @ApiOperation("获取用户列表（条件+分页）")
    public JsonVO<PageDTO<UsersPageDTO>> queryAll(@Validated UserPageQuery query) {
        PageDTO<UsersPageDTO> list = userService.queryAll(query);
        return JsonVO.success(list, "管理员列表查询成功");
    }

    /**
     * 获取用户详细
     */
    @GetMapping("/query/{id}")
    @ApiOperation("获取用户详细")
    public JsonVO<UsersDTO> query(@PathVariable("id") Long id) {
        UsersDTO usersDTO = userService.queryById(id);
        if (usersDTO == null) {
            return JsonVO.fail("用户不存在");
        }
        return JsonVO.success(usersDTO, "用户查询成功");
    }
    /**
     * 修改状态
     */
    @PutMapping("/update-status")
    @ApiOperation("修改状态")
    public JsonVO<String> updateStatus(@Validated @RequestBody UpdateUsersStatusDTO dto) {
        userService.updateStatus(dto);
        return JsonVO.success("修改状态成功");
    }
}
