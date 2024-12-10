package homework.web.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;

/**
 * 作业提交参数
 *
 * @author 30597
 * @since 2024-12-03 9:43
 */
@Data
public class AssignmentSubmitParam implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -66817178147424480L;
    @Schema(description = "作业ID")
    @NotNull(message = "作业ID不能为空")
    private Long assignmentId;

    /**
     * 提交内容
     */
    @Schema(description = "提交内容")
    private String content;

    /**
     * 文件URL
     */
    @Schema(description = "文件URL")
    private String fileUrl;
}
