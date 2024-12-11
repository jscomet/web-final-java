package homework.web.entity.vo;

import homework.web.entity.dto.TestRecordCommitParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class TestRecordResultVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -66817178147424480L;
    @Schema(description = "总分数")
    private Float totalScore;
    @Schema(description = "最高分数")
    private Float maxScore;

    @Schema(description = "题目分数 questionId:score")
    private Map<Long,Float> questionScore;

    @Schema(description = "正确答案列表 questionId:answer")
    private Map<Long,String> correctAnswers;
}