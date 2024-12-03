package homework.web.controller;

import homework.web.entity.dto.QuestionBankQuery;
import homework.web.entity.po.QuestionBank;
import homework.web.entity.vo.QuestionBankVO;
import homework.web.service.QuestionBankService;
import homework.web.util.AuthUtils;
import homework.web.util.beans.CommonResult;
import homework.web.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//TODO question格式需要确认
/**
 * 题库(QuestionBank)表控制层
 *
 * @author jscomet
 * @since 2024-12-02 22:51:55
 */
@Tag(name = "QuestionBank", description = "题库")
@RestController
@RequestMapping("/question-bank")
public class QuestionBankController {
    @Resource
    private QuestionBankService questionBankService;

    @Operation(summary = "获取指定题库题目信息")
    @GetMapping("/info/{id}")
    public CommonResult<QuestionBankVO> getQuestionBank(@PathVariable Long id) {
        QuestionBankVO vo = questionBankService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取题库题目列表")
    @GetMapping("/list")
    public CommonResult<ListResult<QuestionBankVO>> getQuestionBanks(@RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            QuestionBankQuery param) {
        List<QuestionBankVO> list = questionBankService.queryAll(current, pageSize, param);
        int total = questionBankService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "添加题库题目")
    @PostMapping("/add")
    public CommonResult<Boolean> addQuestionBank(@RequestBody QuestionBank param) {
        param.setCreatorId(AuthUtils.getCurrentUserId());
        return questionBankService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定题库信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateQuestionBank(@PathVariable Long id,
            @RequestBody QuestionBank param) {
            param.setQuestionId(id);
        return questionBankService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定题库题目")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteQuestionBank(@PathVariable Long id) {
        return questionBankService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }
}
