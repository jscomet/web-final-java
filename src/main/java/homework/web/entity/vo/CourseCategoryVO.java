package homework.web.entity.vo;

import homework.web.config.valid.QueryGroup;
import homework.web.config.valid.SorterValidated;
import homework.web.entity.po.CourseCategory;
import homework.web.util.beans.Sorter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程分类(CourseCategory)视图模型
 *
 * @author jscomet
 * @since 2024-12-02 21:54:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseCategoryVO extends CourseCategory {

}
