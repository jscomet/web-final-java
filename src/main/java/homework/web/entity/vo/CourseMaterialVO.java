package homework.web.entity.vo;

import homework.web.entity.po.CourseMaterial;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课件资料(CourseMaterial)视图模型
 *
 * @author jscomet
 * @since 2024-12-02 21:54:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseMaterialVO extends CourseMaterial {
    @Schema(description = "课程信息")
    private CourseVO course;
}
