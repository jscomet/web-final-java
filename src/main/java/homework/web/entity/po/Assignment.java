package homework.web.entity.po;

import homework.web.config.valid.AddGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 作业(Assignment)实体类
 *
 * @author jscomet
 * @since 2024-12-02 21:53:12
 */
@Data
public class Assignment implements Serializable {
    @Serial
    private static final long serialVersionUID = -35865321267361693L;
    /**
     * 作业ID
     */
    @Schema(description = "作业ID")    
    @TableId(value = "assignment_id",type = IdType.AUTO)
    private Long assignmentId;


    /**
     * 课程ID
     */
    @Schema(description = "课程ID")    
    @TableField(value = "course_id")
    @NotNull(message = "课程ID不能为空", groups = {AddGroup.class})
    private Long courseId;

    /**
     * 作业标题
     */
    @Schema(description = "作业标题")    
    @TableField(value = "title")
    @NotBlank(message = "作业标题不能为空", groups = {AddGroup.class})
    private String title;

    /**
     * 作业描述
     */
    @Schema(description = "作业描述")    
    @TableField(value = "description")
    @NotBlank(message = "作业描述不能为空", groups = {AddGroup.class})
    private String description;

    /**
     * 截止时间
     */
    @Schema(description = "截止时间")    
    @TableField(value = "deadline")
    @NotNull(message = "截止时间不能为空", groups = {AddGroup.class})
    private LocalDateTime deadline;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")    
    @TableField(value = "create_time")
    @NotNull(message = "创建时间不能为空", groups = {AddGroup.class})
    private LocalDateTime createTime;


}
