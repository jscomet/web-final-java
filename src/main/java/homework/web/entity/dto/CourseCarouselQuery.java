package homework.web.entity.dto;

import homework.web.entity.po.CourseCarousel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程轮播图(CourseCarousel)查询参数
 *
 * @author jscomet
 * @since 2024-12-02 21:53:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseCarouselQuery extends CourseCarousel {
}
