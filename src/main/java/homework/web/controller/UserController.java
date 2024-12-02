package homework.web.controller;

import homework.web.entity.dto.UserQuery;
import homework.web.entity.po.User;
import homework.web.entity.vo.UserVO;
import homework.web.service.UserService;
import homework.web.util.beans.CommonResult;
import homework.web.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户(User)表控制层
 *
 * @author jscomet
 * @since 2024-12-02 19:36:56
 */
@Tag(name = "User", description = "用户")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @Operation(summary = "获取指定用户信息")
    @GetMapping("/info/{id}")
    public CommonResult<UserVO> getUser(@PathVariable Long id) {
        UserVO vo = userService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    public CommonResult<ListResult<UserVO>> getUsers(@RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            UserQuery param) {
        List<UserVO> list = userService.queryAll(current, pageSize, param);
        int total = userService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "添加用户")
    @PostMapping("/add")
    public CommonResult<Boolean> addUser(@RequestBody User param) {
        return userService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定用户信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateUser(@PathVariable Long id,
            @RequestBody User param) {
            param.setUserId(id);
        return userService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定用户")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteUser(@PathVariable Long id) {
        return userService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }
}
