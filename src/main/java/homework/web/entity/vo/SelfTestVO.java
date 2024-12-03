package homework.web.entity.vo;

import homework.web.entity.po.QuestionBank;
import homework.web.entity.po.SelfTest;
import homework.web.entity.po.TestQuestion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 自测试卷(SelfTest)视图模型
 *
 * @author jscomet
 * @since 2024-12-02 22:51:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SelfTestVO extends SelfTest {
    /**
     * 题目列表
     */
    @Schema(description = "题目列表")
    private List<QuestionBank> questions;
}
