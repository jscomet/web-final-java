package homework.web.controller;

import cn.hutool.core.bean.BeanUtil;
import homework.web.annotation.PermissionAuthorize;
import homework.web.config.valid.AddGroup;
import homework.web.config.valid.UpdateGroup;
import homework.web.constant.enums.RoleType;
import homework.web.entity.dto.*;
import homework.web.entity.po.User;
import homework.web.entity.po.UserRole;
import homework.web.entity.vo.UserAuthVO;
import homework.web.entity.vo.UserVO;
import homework.web.service.RoleService;
import homework.web.service.SystemConfigService;
import homework.web.service.UserRoleService;
import homework.web.service.UserService;
import homework.web.util.AssertUtils;
import homework.web.util.AuthUtils;
import homework.web.util.beans.CommonResult;
import homework.web.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户(User)表控制层
 *
 * @author jscomet
 * @since 2024-12-02 20:23:51
 */
@Tag(name = "UserManagement", description = "用户管理")
@RestController
@RequestMapping("/user-management")
public class UserManagementController {
    @Resource
    private UserService userService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private SystemConfigService systemConfigService;

    @Operation(summary = "获取指定用户信息")
    @GetMapping("/info/{id}")
    @PermissionAuthorize({RoleType.TEACHER})
    public CommonResult<UserVO> getUser(@PathVariable Long id) {
        UserVO vo = userService.queryById(id);
        userService.desensitize(vo);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取所有用户列表")
    @GetMapping("/list")
    @PermissionAuthorize({RoleType.TEACHER})
    public CommonResult<ListResult<UserVO>> getUsers(@RequestParam(defaultValue = "1") Integer current,
                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                     UserQuery param) {
        List<UserVO> list = userService.queryAll(current, pageSize, param);
        list.forEach(userService::desensitize);
        int total = userService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "获取学生列表")
    @GetMapping("/list-students")
    @PermissionAuthorize({RoleType.TEACHER})
    public CommonResult<ListResult<UserVO>> getStudents(@RequestParam(defaultValue = "1") Integer current,
                                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                                        UserQuery param) {
        param.setRoleIds(List.of(RoleType.STUDENT.getValue()));
        List<UserVO> list = userService.queryAll(current, pageSize, param);
        list.forEach(userService::desensitize);
        int total = userService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "添加用户")
    @PostMapping("/add")
    @PermissionAuthorize(RoleType.TEACHER)
    public CommonResult<Boolean> addUser(@RequestBody @Validated(AddGroup.class) UserForm param) {
        return userService.saveByForm(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定用户信息")
    @PutMapping("/update/{id}")
    @PermissionAuthorize(RoleType.TEACHER)
    public CommonResult<Boolean> updateUser(@PathVariable Long id,
                                            @RequestBody @Validated(UpdateGroup.class) UserForm param) {
        return userService.updateByForm(id, param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定用户")
    @DeleteMapping("/delete/{id}")
    @PermissionAuthorize(RoleType.TEACHER)
    public CommonResult<Boolean> deleteUser(@PathVariable Long id) {
        return userService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "重置密码")
    @PutMapping("/reset-password")
    @PermissionAuthorize(RoleType.TEACHER)
    public CommonResult<Boolean> resetPassword(@RequestBody @Validated UserResetPasswordParam param) {
        return userService.resetPassword(param.getUserId(), param.getNewPassword()) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "启用禁用用户")
    @PutMapping("/enable-disable/{id}")
    @PermissionAuthorize(RoleType.TEACHER)
    public CommonResult<Boolean> enableDisableUser(@PathVariable Long id) {
        return userService.enableDisableUser(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "是否启用用户自主注册")
    @GetMapping("/is-enable-user-register")
    public CommonResult<Boolean> isEnableSelfRegister() {
        return CommonResult.success(systemConfigService.isEnableSelfRegister());
    }

    @Operation(summary = "设置是否启用用户自主注册")
    @PutMapping("/set-enable-user-register")
    @PermissionAuthorize(RoleType.TEACHER)
    public CommonResult<Boolean> setEnableUserRegister(@RequestParam Boolean enable) {
        return systemConfigService.setEnableSelfRegister(enable) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }


}
