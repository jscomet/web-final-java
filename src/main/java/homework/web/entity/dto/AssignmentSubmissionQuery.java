package homework.web.entity.dto;

import homework.web.entity.po.AssignmentSubmission;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 作业提交(AssignmentSubmission)查询参数
 *
 * @author jscomet
 * @since 2024-12-02 21:53:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssignmentSubmissionQuery extends AssignmentSubmission {
}
