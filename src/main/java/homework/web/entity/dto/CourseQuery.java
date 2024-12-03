package homework.web.entity.dto;

import homework.web.config.valid.QueryGroup;
import homework.web.config.valid.SorterValidated;
import homework.web.entity.po.Course;
import homework.web.util.beans.Sorter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课程信息(Course)查询参数
 *
 * @author jscomet
 * @since 2024-12-02 21:53:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseQuery extends Course {
    @SorterValidated(groups = {QueryGroup.class})
    @Schema(description = "可选字段 发布时间:createTime 、更新时间 updateTime、点赞人数 likeCount、学生人数 studentCount 浏览次数 viewCount ")
    private Sorter sorter;
}
