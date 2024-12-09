package homework.web.entity.vo;

import homework.web.entity.po.TestRecord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 考试记录(TestRecord)视图模型
 *
 * @author jscomet
 * @since 2024-12-02 22:51:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TestRecordVO extends TestRecord {
    @Schema(description = "试卷标题")
    private String title;
    @Schema(description = "试卷信息")
    private SelfTestVO test;
    @Schema(description = "课程信息")
    private CourseVO course;
}
