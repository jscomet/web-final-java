package homework.web.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import homework.web.config.valid.AddGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * Description
 *
 * @author 30597
 * @since 2024-12-03 9:43
 */
@Data
public class AssignmentSubmissionCommitParam implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -66817178147424480L;


    /**
     * 提交内容
     */
    @Schema(description = "提交内容")
    @TableField(value = "content")
    @NotBlank(message = "提交内容不能为空", groups = {AddGroup.class})
    private String content;

    /**
     * 文件URL
     */
    @Schema(description = "文件URL")
    @TableField(value = "file_url")
    private String fileUrl;
}
