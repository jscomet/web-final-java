package homework.web.entity.dto;

import homework.web.entity.po.CourseCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程分类(CourseCategory)查询参数
 *
 * @author jscomet
 * @since 2024-12-02 21:54:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseCategoryQuery extends CourseCategory {
}
