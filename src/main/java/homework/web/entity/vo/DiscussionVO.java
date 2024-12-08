package homework.web.entity.vo;

import homework.web.entity.po.Discussion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

/**
 * 讨论区(Discussion)视图模型
 *
 * @author jscomet
 * @since 2024-12-02 21:54:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DiscussionVO extends Discussion {

    @Schema(description = "课程信息")
    private CourseVO course;

    @Schema(description = "用户信息")
    private UserVO user;

    @Schema(description = "是为否教师")
    private Boolean isTeacher;

    @Schema(description = "回复数")
    private Integer replayCount;

}
