package homework.web.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 讨论区(Discussion)实体类
 *
 * @author jscomet
 * @since 2024-12-02 21:54:31
 */
@Data
public class Discussion implements Serializable {
    @Serial
    private static final long serialVersionUID = 139293122130714971L;
    /**
     * 讨论ID
     */
    @Schema(description = "讨论ID")    
    @TableId(value = "discussion_id",type = IdType.AUTO)
    private Long discussionId;


    /**
     * 课程ID
     */
    @Schema(description = "课程ID")    
    @TableField(value = "course_id")
    private Long courseId;

    /**
     * 创建人id
     */
    @Schema(description = "创建人id")
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 标题
     */
    @Schema(description = "标题")
    @TableField(value = "title")
    private String title;

    /**
     * 讨论内容
     */
    @Schema(description = "讨论内容")    
    @TableField(value = "content")
    private String content;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")    
    @TableField(value = "create_time")
    private LocalDateTime createTime;


}
