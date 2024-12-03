package homework.web.entity.dto;

import homework.web.entity.po.QuestionBank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 题库(QuestionBank)查询参数
 *
 * @author jscomet
 * @since 2024-12-02 22:51:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionBankQuery extends QuestionBank {
}
