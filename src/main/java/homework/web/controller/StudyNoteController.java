package homework.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import homework.web.entity.dto.StudyNoteQuery;
import homework.web.entity.po.Course;
import homework.web.entity.po.StudyNote;
import homework.web.entity.vo.StudyNoteVO;
import homework.web.service.CourseService;
import homework.web.service.StudyNoteService;
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
 * 学习笔记(StudyNote)表控制层
 *
 * @author jscomet
 * @since 2024-12-02 22:51:31
 */
@Tag(name = "StudyNote", description = "学习笔记")
@RestController
@RequestMapping("/study-note")
public class StudyNoteController {
    @Resource
    private StudyNoteService studyNoteService;
    @Resource
    private CourseService courseService;

    @Operation(summary = "获取指定学习笔记信息")
    @GetMapping("/info/{id}")
    public CommonResult<StudyNoteVO> getStudyNote(@PathVariable Long id) {
        StudyNoteVO vo = studyNoteService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取学习笔记列表")
    @GetMapping("/list")
    public CommonResult<ListResult<StudyNoteVO>> getStudyNotes(@RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            StudyNoteQuery param) {
        List<StudyNoteVO> list = studyNoteService.queryAll(current, pageSize, param);
        int total = studyNoteService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    /**
     * 添加学习笔记
     * @param courseId 课程id
     * @param param 学习笔记信息
     * @return 是否添加成功
     */
    @Operation(summary = "添加学习笔记")
    @PostMapping("/add/{courseId}")
    public CommonResult<Boolean> addStudyNote(@PathVariable Long courseId,@RequestBody StudyNote param) {
        // 检查课程是否存在
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getCourseId, courseId);
        AssertUtils.isTrue(courseService.exists(wrapper), HttpStatus.NOT_FOUND, "课程不存在");

        param.setCourseId(courseId);
        param.setStudentId(AuthUtils.getCurrentUserId());
        return studyNoteService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定学习笔记信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateStudyNote(@PathVariable Long id,
            @RequestBody @Validated StudyNote param) {
            param.setNoteId(id);
        return studyNoteService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定学习笔记")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteStudyNote(@PathVariable Long id) {
        return studyNoteService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }
}
