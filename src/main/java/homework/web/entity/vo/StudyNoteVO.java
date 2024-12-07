package homework.web.entity.vo;

import homework.web.entity.po.StudyNote;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 学习笔记(StudyNote)视图模型
 *
 * @author jscomet
 * @since 2024-12-02 22:51:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudyNoteVO extends StudyNote {
    @Schema(description = "课程信息")
    private CourseVO course;
    @Schema(description = "用户信息")
    private UserVO student;
}
