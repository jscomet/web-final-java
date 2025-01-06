package homework.web.controller;

import homework.web.entity.dto.CourseMaterialQuery;
import homework.web.entity.po.CourseMaterial;
import homework.web.entity.vo.CourseMaterialVO;
import homework.web.service.CourseMaterialService;
import homework.web.util.beans.CommonResult;
import homework.web.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课件资料(CourseMaterial)表控制层
 *
 * @author jscomet
 * @since 2024-12-02 21:54:21
 */
@Tag(name = "CourseMaterial", description = "课件资料")
@RestController
@RequestMapping("/course-material")
public class CourseMaterialController {
    @Resource
    private CourseMaterialService courseMaterialService;

    @Operation(summary = "获取指定课件资料信息")
    @GetMapping("/info/{id}")
    public CommonResult<CourseMaterialVO> getCourseMaterial(@PathVariable Long id) {
        CourseMaterialVO vo = courseMaterialService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取课件资料给列表")
    @GetMapping("/list")
    public CommonResult<ListResult<CourseMaterialVO>> getCourseMaterials(@RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            CourseMaterialQuery param) {
        List<CourseMaterialVO> list = courseMaterialService.queryAll(current, pageSize, param);
        int total = courseMaterialService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "添加课件资料")
    @PostMapping("/add")
    public CommonResult<Boolean> addCourseMaterial(@RequestBody CourseMaterial param) {
        return courseMaterialService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定课件资料信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateCourseMaterial(@PathVariable Long id,
            @RequestBody CourseMaterial param) {
            param.setMaterialId(id);
        return courseMaterialService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定课件资料")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteCourseMaterial(@PathVariable Long id) {
        return courseMaterialService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取指定课程的课件资料")
    @GetMapping("/info/courseContent/{courseId}")
    public CommonResult<List<CourseMaterial>> getCourseMaterialsByCourseId(@PathVariable Long courseId) {
        List<CourseMaterial> materials = courseMaterialService.queryByCourseId(courseId);
        return materials != null && !materials.isEmpty() ? CommonResult.success(materials) : CommonResult.error(HttpStatus.NOT_FOUND);
    }
}
