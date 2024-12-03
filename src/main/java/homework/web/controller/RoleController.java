package homework.web.controller;

import homework.web.entity.dto.RoleQuery;
import homework.web.entity.po.Role;
import homework.web.entity.vo.RoleVO;
import homework.web.service.RoleService;
import homework.web.util.beans.CommonResult;
import homework.web.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色(Role)表控制层
 *
 * @author jscomet
 * @since 2024-12-02 20:26:45
 */
@Tag(name = "Role", description = "角色")
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @Operation(summary = "获取指定角色信息")
    @GetMapping("/info/{id}")
    public CommonResult<RoleVO> getRole(@PathVariable Integer id) {
        RoleVO vo = roleService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取角色列表")
    @GetMapping("/list")
    public CommonResult<ListResult<RoleVO>> getRoles(@RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            RoleQuery param) {
        List<RoleVO> list = roleService.queryAll(current, pageSize, param);
        int total = roleService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "添加角色")
    @PostMapping("/add")
    public CommonResult<Boolean> addRole(@RequestBody Role param) {
        return roleService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定角色信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateRole(@PathVariable Integer id,
            @RequestBody Role param) {
            param.setRoleId(id);
        return roleService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定角色")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteRole(@PathVariable Integer id) {
        return roleService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }
}
