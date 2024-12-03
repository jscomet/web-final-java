package homework.web.controller;

import homework.web.entity.dto.CourseCategoryQuery;
import homework.web.entity.po.CourseCategory;
import homework.web.entity.vo.CourseCategoryVO;
import homework.web.service.CourseCategoryService;
import homework.web.util.beans.CommonResult;
import homework.web.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程分类(CourseCategory)表控制层
 *
 * @author jscomet
 * @since 2024-12-02 21:54:00
 */
@Tag(name = "CourseCategory", description = "课程分类")
@RestController
@RequestMapping("/course-category")
public class CourseCategoryController {
    @Resource
    private CourseCategoryService courseCategoryService;

    @Operation(summary = "获取指定课程分类信息")
    @GetMapping("/info/{id}")
    public CommonResult<CourseCategoryVO> getCourseCategory(@PathVariable Long id) {
        CourseCategoryVO vo = courseCategoryService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取课程分类列表")
    @GetMapping("/list")
    public CommonResult<ListResult<CourseCategoryVO>> getCourseCategorys(@RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            CourseCategoryQuery param) {
        List<CourseCategoryVO> list = courseCategoryService.queryAll(current, pageSize, param);
        int total = courseCategoryService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "添加课程分类")
    @PostMapping("/add")
    public CommonResult<Boolean> addCourseCategory(@RequestBody CourseCategory param) {
        return courseCategoryService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定课程分类信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateCourseCategory(@PathVariable Long id,
            @RequestBody CourseCategory param) {
            param.setCategoryId(id);
        return courseCategoryService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定课程分类")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteCourseCategory(@PathVariable Long id) {
        return courseCategoryService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }
}
