package homework.web.entity.vo;

import homework.web.entity.po.CourseEnrollment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程注册(CourseEnrollment)视图模型
 *
 * @author jscomet
 * @since 2024-12-02 21:54:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseEnrollmentVO extends CourseEnrollment {
    @Schema(description = "课程信息")
    private CourseVO course;
    @Schema(description = "学生信息")
    private UserVO student;
}
