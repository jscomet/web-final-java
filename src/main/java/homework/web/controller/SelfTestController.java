package homework.web.controller;

import homework.web.annotation.PermissionAuthorize;
import homework.web.entity.dto.SelfTestCreateParam;
import homework.web.entity.dto.SelfTestQuery;
import homework.web.entity.dto.SelfTestWithRecordQuery;
import homework.web.entity.po.QuestionBank;
import homework.web.entity.po.SelfTest;
import homework.web.entity.vo.SelfTestVO;
import homework.web.entity.vo.SelfTestWithRecordVO;
import homework.web.service.QuestionBankService;
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
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

import homework.web.entity.po.TestRecord;

import homework.web.service.TestRecordService;
import homework.web.util.AIHelperUtils;

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
    @Resource
    private QuestionBankService questionBankService;

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

    @Operation(summary = "获取自测试卷详情列表，包括测试记录")
    @GetMapping("/list-with-record")
    public CommonResult<ListResult<SelfTestWithRecordVO>> getSelfTestsWithRecords(@RequestParam(defaultValue = "1") Integer current,
                                                                                 @RequestParam(defaultValue = "10") Integer pageSize, SelfTestWithRecordQuery param) {
        List<SelfTestWithRecordVO> list = selfTestService.queryAllWithRecord(current, pageSize, param);
        int total = selfTestService.countWithRecord(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "获取我的自测试卷详情列表，包括测试记录")
    @GetMapping("/list-self-with-record")
    @PermissionAuthorize
    public CommonResult<ListResult<SelfTestWithRecordVO>> getMySelfTestsWithRecords(@RequestParam(defaultValue = "1") Integer current,
                                                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                                                  SelfTestWithRecordQuery param) {
        param.setStudentId(AuthUtils.getCurrentUserId());
        List<SelfTestWithRecordVO> list = selfTestService.queryAllWithRecord(current, pageSize, param);
        int total = selfTestService.countWithRecord(param);
        return CommonResult.success(new ListResult<>(list, total));
    }


    @Operation(summary = "添加自测试卷")
    @PostMapping("/add")
    public CommonResult<Boolean> addSelfTest(@RequestBody SelfTestCreateParam param) {
        param.setCreatorId(AuthUtils.getCurrentUserId());
        return selfTestService.create(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    /**
     * 发布测试试卷到指定课程的学生（已废弃）
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

    /**
     * 获取该课程对应得题目
     */
    @Operation(summary = "获取该课程对应得题目")
    @GetMapping("/getQuestion/{id}")
    public CommonResult<List<QuestionBank>> getQuestion(@PathVariable int id) {
        List<QuestionBank> questions = questionBankService.getQuestionIdsByCourseId(id);
        if (questions != null && !questions.isEmpty()) {
            return CommonResult.success(questions);
        }
        return CommonResult.error(HttpStatus.NOT_FOUND);
    }


    @Resource
    private TestRecordService testRecordService;
    private final ObjectMapper objectMapper = new ObjectMapper(); // 用于 JSON 解析
    @Operation(summary = "自动评分自测试卷")
    @PostMapping("/score/{testId}/{studentId}")
    public CommonResult<Map<String, Object>> scoreSelfTest(@PathVariable Long testId, @PathVariable Long studentId) {
        // 获取学生的测试记录
        TestRecord testRecord = testRecordService.getTestRecordByTestIdAndStudentId(testId, studentId);
        if (testRecord == null) {
            return CommonResult.error(HttpStatus.NOT_FOUND, "未找到学生测试记录");
        }

        // 将 answers 字段解析为 Map
        Map<String, String> answersMap;
        try {
            answersMap = objectMapper.readValue(testRecord.getAnswers(), Map.class);
        } catch (Exception e) {
            return CommonResult.error(HttpStatus.INTERNAL_SERVER_ERROR, "答案解析失败");
        }

        // 获取试卷中的所有题目
        List<QuestionBank> questionBanks = questionBankService.getQuestionsByTestId(testId);

        // 初始化总分
        float totalScore = 0;
        float maxScore = 0;

        Map<String, Object> result = new HashMap<>();

        // 遍历题目，逐一评分
        for (QuestionBank question : questionBanks) {
            String studentAnswer = answersMap.get(question.getQuestionId().toString());
            float questionScore = 0;

            switch (question.getType()) {
                case 0: // 单选题
                case 1: // 多选题
                case 2: // 判断题
                    if (studentAnswer != null && studentAnswer.equals(question.getCorrectAnswer())) {
                        questionScore = 1;
                    }
                    break;

                case 3: // 填空题
                    if (studentAnswer != null && studentAnswer.equals(question.getCorrectAnswer())) {
                        questionScore = 1;
                    }
                    break;

                case 4: // 问答题
                    // 调用 LLM 来评分并传递学生答案与正确答案
                    String aiFeedback = AIHelperUtils.aiAnalyse("学生答案: " + studentAnswer + " 正确答案: " + question.getCorrectAnswer());

                    // 记录AI的反馈
                    result.put(question.getQuestionId().toString(), aiFeedback); // 存储每个问题的反馈

                    // 这里可以根据反馈来决定是否给予分数
                    if (aiFeedback.contains("正确")) {
                        questionScore = 1; // 满分1分，如果AI判断为正确
                    }
                    break;

                default:
                    break;
            }

            // 累加分数
            totalScore += questionScore;
            maxScore += 1; // 假设每题满分1分
        }

        // 返回评分结果
        result.put("totalScore", totalScore);
        result.put("maxScore", maxScore);
        return CommonResult.success(result);
    }
}
