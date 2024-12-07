package homework.web.controller;

import cn.hutool.core.bean.BeanUtil;
import homework.web.annotation.PermissionAuthorize;
import homework.web.config.valid.AddGroup;
import homework.web.config.valid.UpdateGroup;
import homework.web.constant.enums.RoleType;
import homework.web.entity.dto.AssignmentFeedbackParam;
import homework.web.entity.dto.AssignmentSubmissionCommitParam;
import homework.web.entity.dto.AssignmentSubmissionExportParam;
import homework.web.entity.dto.AssignmentSubmissionQuery;
import homework.web.entity.po.AssignmentSubmission;
import homework.web.entity.vo.AssignmentSubmissionVO;
import homework.web.entity.vo.AssignmentSubmitStatVO;
import homework.web.service.AssignmentSubmissionService;
import homework.web.util.AssertUtils;
import homework.web.util.AuthUtils;
import homework.web.util.ExcelUtils;
import homework.web.util.beans.CommonResult;
import homework.web.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 作业提交(AssignmentSubmission)表控制层
 *
 * @author jscomet
 * @since 2024-12-02 21:53:25
 */
@Tag(name = "AssignmentSubmission", description = "学生作业提交相关")
@RestController
@RequestMapping("/assignment-submission")
public class AssignmentSubmissionController {
    @Resource
    private AssignmentSubmissionService assignmentSubmissionService;

    @Operation(summary = "获取指定作业提交信息")
    @GetMapping("/info/{id}")
    public CommonResult<AssignmentSubmissionVO> getAssignmentSubmission(@PathVariable Long id) {
        AssignmentSubmissionVO vo = assignmentSubmissionService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取作业提交列表")
    @GetMapping("/list")
    public CommonResult<ListResult<AssignmentSubmissionVO>> getAssignmentSubmissions(@RequestParam(defaultValue = "1") Integer current,
                                                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                                                     AssignmentSubmissionQuery param) {
        List<AssignmentSubmissionVO> list = assignmentSubmissionService.queryAll(current, pageSize, param);
        int total = assignmentSubmissionService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }
    @Operation(summary = "获取我的作业提交列表")
    @GetMapping("/list-self")
    @PermissionAuthorize
    public CommonResult<ListResult<AssignmentSubmissionVO>> getMyAssignmentSubmissions(@RequestParam(defaultValue = "1") Integer current,
                                                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                                                     AssignmentSubmissionQuery param) {
        Long studentId = AuthUtils.getCurrentUserId();
        param.setStudentId(studentId);
        List<AssignmentSubmissionVO> list = assignmentSubmissionService.queryAll(current, pageSize, param);
        int total = assignmentSubmissionService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    /**
     * 学生提交作业
     *
     * @param id          提交作业ID
     * @param param        作业提交信息
     * @return 是否提交成功
     */
    @Operation(summary = "学生提交作业")
    @PostMapping("/commit/{id}")
    public CommonResult<Boolean> commitAssignmentSubmission(@PathVariable Long id, @RequestBody @Validated(AddGroup.class) AssignmentSubmissionCommitParam param) {
        Long studentId = AuthUtils.getCurrentUserId();
        return assignmentSubmissionService.commit(id, studentId, param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    /**
     * 老师给作业评分和反馈
     *
     * @param id    作业提交ID
     * @param param 作业评分和反馈信息
     * @return 是否修改成功
     */
    @Operation(summary = "老师修改给作业提交评分和反馈")
    @PutMapping("/correct/{id}")
    @PermissionAuthorize(RoleType.TEACHER)
    public CommonResult<Boolean> correctAssignmentSubmission(@PathVariable Long id,
                                                             @RequestBody @Validated(UpdateGroup.class) AssignmentFeedbackParam param) {
        Long teacherId = AuthUtils.getCurrentUserId();
        return assignmentSubmissionService.correct(id, teacherId, param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定作业提交")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteAssignmentSubmission(@PathVariable Long id) {
        return assignmentSubmissionService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "根据条件获取学生作业提交情况")
    @GetMapping("/statistic")
    public CommonResult<AssignmentSubmitStatVO> getAssignmentSubmitStat(AssignmentSubmissionQuery param) {
        return CommonResult.success(assignmentSubmissionService.querySubmitStat(param));
    }


}
