package homework.web.entity.vo;

import homework.web.entity.po.Assignment;
import homework.web.entity.po.AssignmentSubmission;
import homework.web.entity.po.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 作业详情视图视图模型
 * Description
 * @since 2024-12-02 21:53:13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssignmentDetailVO extends AssignmentVO {

    @Schema(description = "学生Id")
    private Long studentId;

    @Schema(description = "学生信息")
    private UserVO student;

    @Schema(description = "作业提交信息Id")
    private Long submissionId;
    @Schema(description = "作业提交状态")
    private AssignmentSubmission.Status submissionStatus;
    @Schema(description = "作业提交时间")
    private Long submitTime;

    @Schema(description = "作业提交内容")
    private AssignmentSubmission submission;
}
