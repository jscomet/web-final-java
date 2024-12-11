package homework.web.controller;

import homework.web.annotation.PermissionAuthorize;
import homework.web.entity.dto.TestRecordCommitParam;
import homework.web.entity.dto.TestRecordQuery;
import homework.web.entity.po.TestRecord;
import homework.web.entity.vo.TestRecordResultVO;
import homework.web.entity.vo.TestRecordVO;
import homework.web.service.TestRecordService;
import homework.web.service.impl.SelfTestServiceImpl;
import homework.web.util.AuthUtils;
import homework.web.util.beans.CommonResult;
import homework.web.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 考试记录(TestRecord)表控制层
 *
 * @author jscomet
 * @since 2024-12-02 22:50:41
 */
@Tag(name = "TestRecord", description = "考试记录")
@RestController
@RequestMapping("/test-record")
public class TestRecordController {
    @Resource
    private TestRecordService testRecordService;
    @Autowired
    private SelfTestServiceImpl selfTestService;

    @Operation(summary = "获取指定考试记录信息")
    @GetMapping("/info/{id}")
    public CommonResult<TestRecordVO> getTestRecord(@PathVariable Long id) {
        TestRecordVO vo = testRecordService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取考试记录列表")
    @GetMapping("/list")
    public CommonResult<ListResult<TestRecordVO>> getTestRecords(@RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            TestRecordQuery param) {
        List<TestRecordVO> list = testRecordService.queryAll(current, pageSize, param);
        int total = testRecordService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "获取我的考试记录列表")
    @GetMapping("/list-self")
    @PermissionAuthorize
    public CommonResult<ListResult<TestRecordVO>> getMyTestRecords(@RequestParam(defaultValue = "1") Integer current,
                                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                                 TestRecordQuery param) {
        param.setStudentId(AuthUtils.getCurrentUserId());
        List<TestRecordVO> list = testRecordService.queryAll(current, pageSize, param);
        //循环list，添加考试名称
        for (TestRecordVO testRecordVO : list) {
            testRecordVO.setTitle(selfTestService.queryById(testRecordVO.getTestId()).getTitle());
        }
        int total = testRecordService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }


    @Operation(summary = "添加考试记录")
    @PostMapping("/add")
    public CommonResult<Boolean> addTestRecord(@RequestBody TestRecord param) {
        return testRecordService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定考试记录信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateTestRecord(@PathVariable Long id,
            @RequestBody TestRecord param) {
            param.setRecordId(id);
        return testRecordService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定考试记录")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteTestRecord(@PathVariable Long id) {
        return testRecordService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    //TODO 答案的格式需要确定
    @Operation(summary = "学生提交考试答案")
    @PostMapping("/submit")
    public CommonResult<TestRecordResultVO> submitTestRecord(@RequestBody TestRecordCommitParam answer) {
        TestRecordResultVO vo = testRecordService.commit(AuthUtils.getCurrentUserId(),answer);
        return vo!=null? CommonResult.success(vo) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }
}
