package homework.web.entity.vo;

import homework.web.entity.po.CourseCarousel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程轮播图(CourseCarousel)视图模型
 *
 * @author jscomet
 * @since 2024-12-02 21:53:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseCarouselVO extends CourseCarousel {
    @Schema(description = "课程信息")
    private CourseVO course;
}
