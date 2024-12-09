package homework.web.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;

@Data
public class TestRecordCommitParam {

    @Schema(description = "记录ID")
    private Long recordId;

    @Schema(description = "学生ID")
    private Long studentId;

    @Schema(description = "试卷ID")
    private Long testId;

    @Schema(description = "答案列表")
    private List<Answer> answers;

    @Schema(description = "课程编号")
    private Long courseId;

    @Data
    public static class Answer {
        @Schema(description = "问题ID")
        public Long questionId;

        @Schema(description = "答案")
        public String answer;
    }
}