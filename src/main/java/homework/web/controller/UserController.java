package homework.web.controller;

import homework.web.annotation.PermissionAuthorize;
import homework.web.config.valid.AddGroup;
import homework.web.entity.dto.*;
import homework.web.entity.vo.UserAuthVO;
import homework.web.entity.vo.UserVO;
import homework.web.service.SystemConfigService;
import homework.web.service.UserService;
import homework.web.util.AssertUtils;
import homework.web.util.AuthUtils;
import homework.web.util.beans.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 个人用户控制器
 *
 * @author 30597
 * @since 2024-12-03 1:19
 */
@Tag(name = "User", description = "用户")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private SystemConfigService systemConfigService;

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/current")
    public CommonResult<UserVO> getCurrentUser() {
        UserVO vo = AuthUtils.getUserDetails();
        //信息脱敏
        userService.desensitize(vo);
        return CommonResult.success(vo);
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public CommonResult<UserAuthVO> loginByPassword(@RequestBody @Validated UserLoginPasswordParam param, HttpServletRequest request) {

        String token = userService.loginByPassword(param);

        AssertUtils.notNull(token, HttpStatus.UNAUTHORIZED, "用户名或密码错误");
        UserAuthVO authVO = new UserAuthVO();
        authVO.setToken(token);
        return CommonResult.success(authVO);
    }

    @Operation(summary = "用户学生注册")
    @PostMapping("/register-for-student")
    public CommonResult<UserAuthVO> registerForStudent(@RequestBody @Validated UserStudentRegisterParam param) {
        AssertUtils.isTrue(systemConfigService.isEnableSelfRegister(), HttpStatus.BAD_REQUEST, "学生注册未开放");
        AssertUtils.notEmpty(param.getPassword(), HttpStatus.BAD_REQUEST, "未设置密码");
        String token = userService.registerForStudent(param);
        UserAuthVO authVO = new UserAuthVO();
        authVO.setToken(token);
        return CommonResult.success(authVO);
    }

    @Operation(summary = "老师用户注册")
    @PostMapping("/register-for-teacher")
    public CommonResult<UserAuthVO> registerForTeacher(@RequestBody @Validated UserTeacherRegisterParam param) {
        AssertUtils.isTrue(systemConfigService.isEnableSelfRegister(), HttpStatus.BAD_REQUEST, "老师注册未开放");
        AssertUtils.notEmpty(param.getPassword(), HttpStatus.BAD_REQUEST, "未设置密码");
        String token = userService.registerForTeacher(param);
        UserAuthVO authVO = new UserAuthVO();
        authVO.setToken(token);
        return CommonResult.success(authVO);
    }

    @Operation(summary = "忘记密码")
    @PostMapping("/forget-password")
    public CommonResult<Boolean> forgetPassword(@RequestBody @Validated UserTeacherRegisterParam param) {
        AssertUtils.notEmpty(param.getPassword(), HttpStatus.BAD_REQUEST, "未设置密码");
        boolean t = userService.forgetPassword(param);
        return t ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST, "用户名或学号错误");
    }


    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    @PermissionAuthorize
    public CommonResult<Boolean> logout() {
        userService.logout();
        return CommonResult.success(true);
    }

    @Operation(summary = "修改密码")
    @PutMapping("/update-password")
    @PermissionAuthorize
    public CommonResult<Boolean> updatePassword(@RequestBody @Validated UserPasswordParam param) {
        ;
        param.setUserId(AuthUtils.getCurrentUserId());
        return userService.updatePassword(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }
}
