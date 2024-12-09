package homework.web.entity.dto;

import homework.web.entity.po.AssignmentSubmission;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 作业详情查询参数
 *
 * @author 30597
 * @since 2024-12-10 0:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssignmentDetailQuery extends AssignmentQuery {
    @Schema(description = "学生Id")
    private Long studentId;
    @Schema(description = "作业提交信息Id")
    private Long submissionId;
    @Schema(description = "作业提交状态")
    private AssignmentSubmission.Status submissionStatus;
    @Schema(description = "作业提交状态值")
    private List<AssignmentSubmission.Status> submissionStatusList;
    @Schema(description = "作业提交时间")
    private LocalDateTime submitTime;
}
