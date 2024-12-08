package homework.web.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 考试记录(TestRecord)实体类
 *
 * @author jscomet
 * @since 2024-12-02 22:51:12
 */
@Data
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
    @TableField(value = "answers")
    private String answers;

    /**
     * 得分
     */
    @Schema(description = "得分")
    @TableField(value = "score")
    private Float score;

    /**
     * 完成时间
     */
    @Schema(description = "完成时间")
    @TableField(value = "complete_time")
    private LocalDateTime completeTime;


}
