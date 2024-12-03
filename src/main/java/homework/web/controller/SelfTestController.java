package homework.web.controller;

import homework.web.entity.dto.SelfTestCreateParam;
import homework.web.entity.dto.SelfTestQuery;
import homework.web.entity.po.SelfTest;
import homework.web.entity.vo.SelfTestVO;
import homework.web.service.SelfTestService;
import homework.web.util.AssertUtils;
import homework.web.util.AuthUtils;
import homework.web.util.beans.CommonResult;
import homework.web.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 自测试卷(SelfTest)表控制层
 *
 * @author jscomet
 * @since 2024-12-02 22:51:41
 */
@Tag(name = "SelfTest", description = "自测试卷")
@RestController
@RequestMapping("/self-test")
public class SelfTestController {
    @Resource
    private SelfTestService selfTestService;

    @Operation(summary = "获取指定自测试卷信息")
    @GetMapping("/info/{id}")
    public CommonResult<SelfTestVO> getSelfTest(@PathVariable Long id) {
        SelfTestVO vo = selfTestService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取自测试卷列表")
    @GetMapping("/list")
    public CommonResult<ListResult<SelfTestVO>> getSelfTests(@RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            SelfTestQuery param) {
        List<SelfTestVO> list = selfTestService.queryAll(current, pageSize, param);
        int total = selfTestService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "添加自测试卷")
    @PostMapping("/add")
    public CommonResult<Boolean> addSelfTest(@RequestBody SelfTestCreateParam param) {
        param.setCreatorId(AuthUtils.getCurrentUserId());
        return selfTestService.create(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    /**
     * 发布测试试卷到指定课程的学生
     *
     * @param id 自测试卷id
     * @param courseId 课程id
     * @return 是否发布成功
     */
    @Operation(summary = "发布测试试卷到指定课程的学生")
    @PostMapping("/publish/{id}/{courseId}")
    public CommonResult<Boolean> publishSelfTest(@PathVariable Long id, @PathVariable Long courseId) {
        return selfTestService.publish(id, courseId) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定自测试卷信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateSelfTest(@PathVariable Long id,
            @RequestBody SelfTest param) {
            param.setTestId(id);
        return selfTestService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定自测试卷")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteSelfTest(@PathVariable Long id) {
        return selfTestService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    /**
     * 生成自测试卷
     * @param param 自测试卷信息与设置
     * @return 自测试卷id
     */
    @Operation(summary = "生成自测试卷")
    @PostMapping("/generate")
    public CommonResult<Long> generateSelfTest(@RequestBody SelfTest param) {
        param.setCreatorId(AuthUtils.getCurrentUserId());
        return CommonResult.success(selfTestService.generateSelfTest(param));
    }
}
