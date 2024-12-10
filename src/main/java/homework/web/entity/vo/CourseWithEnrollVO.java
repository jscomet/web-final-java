package homework.web.entity.vo;

import homework.web.entity.po.Course;
import homework.web.entity.po.CourseEnrollment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 课程信息包含注册参数(Course)视图模型
 *
 * @author jscomet
 * @since 2024-12-02 21:53:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseWithEnrollVO extends CourseVO {
    @Schema(description = "学生ID")
    private Long studentId;
    @Schema(description = "学生信息")
    private UserVO student;
    @Schema(description = "注册ID")
    private Long enrollmentId;
    @Schema(description = "注册状态")
    private CourseEnrollment.Status enrollStatus;
    @Schema(description = "注册时间")
    private LocalDateTime enrollTime;
}
