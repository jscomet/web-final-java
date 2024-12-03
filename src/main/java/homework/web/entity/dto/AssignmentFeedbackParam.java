package homework.web.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import homework.web.config.valid.AddGroup;
import homework.web.config.valid.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.io.Serial;

/**
 * Description
 *
 * @author 30597
 * @since 2024-12-03 9:48
 */
@Data
public class AssignmentFeedbackParam implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -66817178147424480L;

    @Schema(description = "提交ID")
    private Long submissionId;

    /**
     * 得分
     */
    @Schema(description = "得分")
    @TableField(value = "score")
    @NotNull(message = "新增时不能添加得分", groups = {UpdateGroup.class})
    private Float score;

    /**
     * 反馈内容
     */
    @Schema(description = "反馈内容")
    private String feedback;
}
