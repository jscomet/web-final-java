package homework.web.entity.vo;

import homework.web.entity.po.TestQuestion;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 试卷题目关联表(TestQuestion)视图模型
 *
 * @author jscomet
 * @since 2024-12-02 22:50:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TestQuestionVO extends TestQuestion {
}
