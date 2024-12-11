package homework.web.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonValue;
import homework.web.entity.dto.TestRecordCommitParam;
import homework.web.entity.handler.AnswersListTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 考试记录(TestRecord)实体类
 *
 * @author jscomet
 * @since 2024-12-02 22:51:12
 */
@Data
@TableName(value = "test_record", autoResultMap = true)
public class TestRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = -63792845478545225L;

    public enum Status implements IEnum<Integer> {
        UNFINISHED(0),
        FINISHED(1);

        @JsonValue
        private final int value;

        Status(int value) {
            this.value = value;
        }


        @Override
        public Integer getValue() {
            return value;
        }
    }

    /**
     * 记录ID
     */
    @Schema(description = "记录ID")
    @TableId(value = "record_id", type = IdType.AUTO)
    private Long recordId;


    /**
     * 试卷id
     */
    @Schema(description = "试卷id")
    @TableField(value = "test_id")
    private Long testId;

    /**
     * 学生ID
     */
    @Schema(description = "学生ID")
    @TableField(value = "student_id")
    private Long studentId;

    /**
     * 测试状态 0未完成 1已完成
     */
    @Schema(description = "测试状态")
    @TableField(value = "status")
    private Status status;
    //TODO 答案的格式需要确定
    /**
     * 答案JSON
     */
    @Schema(description = "答案JSON")
    @TableField(value = "answers",typeHandler = AnswersListTypeHandler.class)
    private List<TestRecordCommitParam.Answer> answers;

    /**
     * 得分
     */
    @Schema(description = "得分")
    @TableField(value = "score")
    private Float score;


    @Schema(description = "最高分数")
    @TableField(value = "max_score")
    private Float maxScore;

    @Schema(description = "题目分数 questionId:score")
    @TableField(value = "question_score",typeHandler = JacksonTypeHandler.class)
    private Map<Long,Float> questionScore;

    @Schema(description = "正确答案列表 questionId:answer")
    @TableField(value = "correct_answers",typeHandler = JacksonTypeHandler.class)
    private Map<Long,String> correctAnswers;

    /**
     * 完成时间
     */
    @Schema(description = "完成时间")
    @TableField(value = "complete_time")
    private LocalDateTime completeTime;

    /**
     * 课程标号
     */
    @Schema(description = "课程标号")
    @TableField(value = "course_id")
    private Long courseId;


}
