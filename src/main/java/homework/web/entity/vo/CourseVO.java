package homework.web.entity.vo;

import homework.web.entity.po.Course;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程信息(Course)视图模型
 *
 * @author jscomet
 * @since 2024-12-02 21:53:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseVO extends Course {
    @Schema(description = "分类名称")
    private CourseCategoryVO category;
    @Schema(description = "教师名称")
    private UserVO teacher;
}
