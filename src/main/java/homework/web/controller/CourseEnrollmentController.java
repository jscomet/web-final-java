package homework.web.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import homework.web.annotation.PermissionAuthorize;
import homework.web.entity.dto.CourseEnrollmentQuery;
import homework.web.entity.po.Course;
import homework.web.entity.po.CourseEnrollment;
import homework.web.entity.vo.CourseEnrollmentVO;
import homework.web.entity.vo.CourseVO;
import homework.web.service.CourseEnrollmentService;
import homework.web.service.CourseService;
import homework.web.util.AssertUtils;
import homework.web.util.AuthUtils;
import homework.web.util.beans.CommonResult;
import homework.web.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 课程注册(CourseEnrollment)表控制层
 *
 * @author jscomet
 * @since 2024-12-02 21:54:11
 */
@Tag(name = "CourseEnrollment", description = "学生课程注册/选课")
@RestController
@RequestMapping("/course-enrollment")
public class CourseEnrollmentController {
    @Resource
    private CourseEnrollmentService courseEnrollmentService;

    @Operation(summary = "获取指定课程注册信息")
    @GetMapping("/info/{id}")
    public CommonResult<CourseEnrollmentVO> getCourseEnrollment(@PathVariable Long id) {
        CourseEnrollmentVO vo = courseEnrollmentService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取课程注册列表")
    @GetMapping("/list")
    public CommonResult<ListResult<CourseEnrollmentVO>> getCourseEnrollments(@RequestParam(defaultValue = "1") Integer current,
                                                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                                                             CourseEnrollmentQuery param) {
        List<CourseEnrollmentVO> list = courseEnrollmentService.queryAll(current, pageSize, param);
        int total = courseEnrollmentService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "获取我的课程注册列表")
    @GetMapping("/list-self")
    @PermissionAuthorize
    public CommonResult<ListResult<CourseEnrollmentVO>> getMyCourseEnrollments(@RequestParam(defaultValue = "1") Integer current,
                                                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                                                             CourseEnrollmentQuery param) {
        param.setStudentId(AuthUtils.getCurrentUserId());
        List<CourseEnrollmentVO> list = courseEnrollmentService.queryAll(current, pageSize, param);
        int total = courseEnrollmentService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }


    /**
     * 学生添加课程注册
     *
     * @param courseId 课程ID
     * @return 是否添加成功
     */
    @Operation(summary = "学生添加课程注册")
    @PostMapping("/add/{courseId}")
    @PermissionAuthorize
    public CommonResult<Boolean> addCourseEnrollment(@PathVariable Long courseId) {
        return courseEnrollmentService.addByCourseId(courseId) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    /**
     * 学生取消课程注册
     *
     * @param id 课程注册ID
     * @return 是否添加成功
     */
    @Operation(summary = "学生取消课程注册")
    @PutMapping("/quit/{id}")
    @PermissionAuthorize
    public CommonResult<Boolean> quitCourseEnrollment(@PathVariable Long id) {
        Long studentId = AuthUtils.getCurrentUserId();
        return courseEnrollmentService.quit(studentId, id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "删除指定课程注册")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteCourseEnrollment(@PathVariable Long id) {
        return courseEnrollmentService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }
}
