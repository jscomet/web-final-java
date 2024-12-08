package homework.web.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import homework.web.config.valid.QueryGroup;
import homework.web.config.valid.SorterValidated;
import homework.web.util.beans.Sorter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 讨论区回复表(DiscussionReply)添加参数
 *
 * @author jscomet
 * @since 2024-12-08 16:07:18
 */
@Data
public class DiscussionReplyForm {

    /**
     * 讨论ID
     */
    @Schema(description = "讨论ID ,如果回复讨论则传讨论ID，如果回复回复则传回复ID")
    private Long discussionId;
    /**
     * 讨论回复id
     */
    @Schema(description = "讨论回复id，如果回复讨论则传null，如果回复回复则传回复ID")
    private Long replyId;

    /**
     * 回复内容
     */
    @Schema(description = "回复内容")
    @NotBlank(message = "回复内容不能为空")
    @NotNull(message = "回复内容不能为空")
    private String content;
}
