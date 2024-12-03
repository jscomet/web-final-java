package homework.web.entity.vo;

import homework.web.entity.po.AssignmentSubmission;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 作业提交(AssignmentSubmission)视图模型
 *
 * @author jscomet
 * @since 2024-12-02 21:53:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssignmentSubmissionVO extends AssignmentSubmission {

}
