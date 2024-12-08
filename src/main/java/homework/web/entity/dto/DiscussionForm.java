package homework.web.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import homework.web.entity.po.Discussion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 讨论区(Discussion)创建参数
 *
 * @author jscomet
 * @since 2024-12-02 21:54:31
 */
@Data
public class DiscussionForm  {
    /**
     * 课程id
     */
    @Schema(description = "课程id")
    @NotNull(message = "课程id不能为空")
    private Long courseId;
    /**
     * 标题
     */
    @Schema(description = "标题")
    @NotNull(message = "标题不能为空")
    private String title;

    /**
     * 讨论内容
     */
    @Schema(description = "讨论内容")
    @NotNull(message = "讨论内容不能为空")
    @NotNull(message = "讨论内容不能为空")
    private String content;
}
