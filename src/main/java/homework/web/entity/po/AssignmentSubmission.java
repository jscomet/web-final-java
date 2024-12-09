package homework.web.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonValue;
import homework.web.config.valid.AddGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 作业提交(AssignmentSubmission)实体类
 *
 * @author jscomet
 * @since 2024-12-02 21:53:25
 */
@Data
public class AssignmentSubmission implements Serializable {
    @Serial
    private static final long serialVersionUID = -66817178147424480L;

    public enum Status implements IEnum<Integer> {
        /**
         * 0，未提交
         */
        UNCOMMITTED(0),
        /**
         * 1,未批改
         */
        UNCORRECTED(1),
        /**
         * 2,已批改
         */
        CORRECTED(2);

        @JsonValue
        private final int value;

        private Status(int value) {
            this.value = value;
        }

        @Override
        public Integer getValue() {
            return value;
        }
    }
    /**
     * 提交ID
     */
    @Schema(description = "提交ID")    
    @TableId(value = "submission_id",type = IdType.AUTO)
    private Long submissionId;


    /**
     * 作业ID
     */
    @Schema(description = "作业ID")    
    @TableField(value = "assignment_id")
    @NotNull(message = "作业ID不能为空", groups = {AddGroup.class})
    private Long assignmentId;

    /**
     * 学生ID
     */
    @Schema(description = "学生ID")    
    @TableField(value = "student_id")
    private Long studentId;

    /**
     * 提交内容
     */
    @Schema(description = "提交内容")    
    @TableField(value = "content")
    private String content;

    /**
     * 文件URL
     */
    @Schema(description = "文件URL")    
    @TableField(value = "file_url")
    private String fileUrl;

    /**
     * 状态：0-未提交、1-未批改、2-已完成
     */
    @Schema(description = "状态：0-未提交、1-未批改、2-已批改")
    @TableField(value = "status")
    private Status status;


    /**
     * 得分
     */
    @Schema(description = "得分")    
    @TableField(value = "score")
    @Null(message = "新增时不能添加得分", groups = {AddGroup.class})
    private Float score;

    /**
     * 反馈内容
     */
    @Schema(description = "反馈内容")    
    @TableField(value = "feedback")
    @Null(message = "新增时不能添加反馈", groups = {AddGroup.class})
    private String feedback;

    /**
     * 提交时间
     */
    @Schema(description = "提交时间")    
    @TableField(value = "submit_time")
    @Null(message = "新增时不能添加反馈", groups = {AddGroup.class})
    private LocalDateTime submitTime;


}
