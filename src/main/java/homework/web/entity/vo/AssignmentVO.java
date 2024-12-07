package homework.web.entity.vo;

import homework.web.entity.po.Assignment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 作业(Assignment)视图模型
 *
 * @author jscomet
 * @since 2024-12-02 21:53:13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssignmentVO extends Assignment {
    /**
     * 作业提交情况统计
     */
    @Schema(description = "作业提交情况统计")
    private AssignmentSubmitStatVO submitStat;

    @Schema(description = "课程信息")
    private CourseVO course;
}
