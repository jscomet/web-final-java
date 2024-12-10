package homework.web.entity.dto;

import homework.web.entity.po.CourseEnrollment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程信息包含注册参数(Course)查询参数
 *
 * @author 30597
 * @since 2024-12-10 15:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseWithEnrollQuery extends CourseQuery{
    @Schema(description = "注册ID")
    private Long enrollmentId;
    @Schema(description = "学生ID")
    private Long studentId;
    @Schema(description = "注册状态 0-选中 ，1-收藏 ，2-取消")
    private CourseEnrollment.Status enrollStatus;
}
