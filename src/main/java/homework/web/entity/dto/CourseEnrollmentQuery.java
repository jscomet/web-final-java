package homework.web.entity.dto;

import homework.web.entity.po.CourseEnrollment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程注册(CourseEnrollment)查询参数
 *
 * @author jscomet
 * @since 2024-12-02 21:54:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseEnrollmentQuery extends CourseEnrollment {
}
