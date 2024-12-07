package homework.web.controller;

import homework.web.config.valid.QueryGroup;
import homework.web.entity.dto.AssignmentQuery;
import homework.web.entity.dto.CourseQuery;
import homework.web.entity.po.Course;
import homework.web.entity.vo.AssignmentVO;
import homework.web.entity.vo.CourseVO;
import homework.web.entity.vo.UserVO;
import homework.web.service.*;
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
 * 课程信息(Course)表控制层
 *
 * @author jscomet
 * @since 2024-12-02 21:53:40
 */
@Tag(name = "Course", description = "课程信息")
@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    private CourseService courseService;
    @Resource
    private UserService userService;
    @Resource
    private AssignmentService assignmentService;

    @Operation(summary = "获取指定课程信息信息")
    @GetMapping("/info/{id}")
    public CommonResult<CourseVO> getCourse(@PathVariable Long id) {
        CourseVO vo = courseService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取课程信息列表")
    @GetMapping("/list")
    public CommonResult<ListResult<CourseVO>> getCourses(@RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @Validated(QueryGroup.class) CourseQuery param) {
        List<CourseVO> list = courseService.queryAll(current, pageSize, param);
        int total = courseService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "对应课程下的所有学生账号")
    @GetMapping("/list-student/{id}")
    public CommonResult<ListResult<UserVO>> getCourseStudents(@PathVariable Long id) {
        List<UserVO> list = userService.queryStudentsByCourseId(id);
        list.forEach(userService::desensitize);
        return CommonResult.success(new ListResult<>(list, list.size()));
    }

    @Operation(summary = "对应课程的作业的提交情况")
    @GetMapping("/list-assignment/{id}")
    public CommonResult<ListResult<AssignmentVO>> getCourseAssignments(@PathVariable Long id) {
        AssignmentQuery query = new AssignmentQuery();
        query.setCourseId(id);
        List<AssignmentVO> list = assignmentService.queryAll(-1, -1, query);
        return CommonResult.success(new ListResult<>(list, list.size()));
    }

    @Operation(summary = "添加课程信息")
    @PostMapping("/add")
    public CommonResult<Boolean> addCourse(@RequestBody Course param) {
        return courseService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定课程信息信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateCourse(@PathVariable Long id,
            @RequestBody Course param) {
            param.setCourseId(id);
        return courseService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定课程信息")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteCourse(@PathVariable Long id) {
        return courseService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }
}
