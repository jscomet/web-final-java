package homework.web.controller;

import homework.web.annotation.PermissionAuthorize;
import homework.web.config.valid.AddGroup;
import homework.web.config.valid.QueryGroup;
import homework.web.entity.dto.AssignmentDetailQuery;
import homework.web.entity.dto.AssignmentQuery;
import homework.web.entity.dto.AssignmentSubmitParam;
import homework.web.entity.po.Assignment;
import homework.web.entity.vo.AssignmentDetailVO;
import homework.web.entity.vo.AssignmentStatVO;
import homework.web.entity.vo.AssignmentVO;
import homework.web.entity.vo.AssignmentWithStatVO;
import homework.web.service.AssignmentService;
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
 * 作业(Assignment)表控制层
 *
 * @author jscomet
 * @since 2024-12-02 21:53:12
 */
@Tag(name = "Assignment", description = "作业相关操作")
@RestController
@RequestMapping("/assignment")
public class AssignmentController {
    @Resource
    private AssignmentService assignmentService;

    @Operation(summary = "获取指定作业信息")
    @GetMapping("/info/{id}")
    public CommonResult<AssignmentVO> getAssignment(@PathVariable Long id) {
        AssignmentVO vo = assignmentService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取作业列表")
    @GetMapping("/list")
    public CommonResult<ListResult<AssignmentVO>> getAssignments(@RequestParam(defaultValue = "1") Integer current,
                                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                                 @Validated(QueryGroup.class) AssignmentQuery param) {
        List<AssignmentVO> list = assignmentService.queryAll(current, pageSize, param);
        int total = assignmentService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "获取作业详情列表，携带提交情况")
    @GetMapping("/list-detail")
    public CommonResult<ListResult<AssignmentDetailVO>> getAssignmentDetails(@RequestParam(defaultValue = "1") Integer current,
                                                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                                                             @Validated(QueryGroup.class) AssignmentDetailQuery param) {

        List<AssignmentDetailVO> list = assignmentService.queryAllDetail(current, pageSize, param);
        int total = assignmentService.countDetail(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "获取我的作业详情列表，携带提交情况")
    @GetMapping("/list-detail-self")
    @PermissionAuthorize
    public CommonResult<ListResult<AssignmentDetailVO>> getMyAssignmentDetails(@RequestParam(defaultValue = "1") Integer current,
                                                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                                                               @Validated(QueryGroup.class) AssignmentDetailQuery param) {

        param.setStudentId(AuthUtils.getCurrentUserId());
        List<AssignmentDetailVO> list = assignmentService.queryAllDetail(current, pageSize, param);
        int total = assignmentService.countDetail(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "获取作业统计情况列表")
    @GetMapping("/list-stats")
    public CommonResult<ListResult<AssignmentWithStatVO>> getAssignmentsWithStats(@RequestParam(defaultValue = "1") Integer current,
                                                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                                                               @Validated(QueryGroup.class) AssignmentDetailQuery param) {

        List<AssignmentWithStatVO> list = assignmentService.queryAllWithStat(current,pageSize, param);
        int total = assignmentService.countDetail(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "获取作业统计情况信息")
    @GetMapping("/stat-info")
    public CommonResult<AssignmentStatVO> getAssignmentStatInfo(@Validated(QueryGroup.class) AssignmentDetailQuery param) {
        return CommonResult.success(assignmentService.queryStat(param));
    }

    @Operation(summary = "发布作业,并为每个选课的学生生成作业提交记录")
    @PostMapping("/publish")
    public CommonResult<Boolean> publishAssignment(@RequestBody @Validated(AddGroup.class) Assignment param) {
        return assignmentService.publish(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "学生提交作业，如果没有提交记录则创建,有则更新")
    @PostMapping("/submit")
    @PermissionAuthorize
    public CommonResult<Boolean> submitAssignment(@RequestBody @Validated AssignmentSubmitParam param) {
        Long studentId = AuthUtils.getCurrentUserId();
        return assignmentService.submit(studentId,param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }


    @Operation(summary = "修改指定作业信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateAssignment(@PathVariable Long id,
                                                  @RequestBody Assignment param) {
        param.setAssignmentId(id);
        return assignmentService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定作业")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteAssignment(@PathVariable Long id) {

        return assignmentService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }


}
