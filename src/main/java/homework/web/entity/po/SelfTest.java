package homework.web.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import homework.web.config.valid.AddGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 自测试卷(SelfTest)实体类
 *
 * @author jscomet
 * @since 2024-12-02 22:51:41
 */
@Data
public class SelfTest implements Serializable {
    @Serial
    private static final long serialVersionUID = 471126000117145829L;
    /**
     * 试卷ID
     */
    @Schema(description = "试卷ID")    
    @TableId(value = "test_id",type = IdType.AUTO)
    private Long testId;


    /**
     * 创建者ID
     */
    @Schema(description = "创建者ID")    
    @TableField(value = "creator_id")
    private Long creatorId;

    /**
     * 试卷所属课程
     */
    @Schema(description = "试卷所属课程id")
    @TableField(value = "course_id")
    private Long courseId;

    /**
     * 试卷状态
     */
    @Schema(description = "试卷状态")
    @TableField(value = "status")
    private Integer status;


    /**
     * 试卷标题
     */
    @Schema(description = "试卷标题")    
    @TableField(value = "title")
    @NotBlank(message = "试卷标题不能为空",groups = {AddGroup.class})
    private String title;

    /**
     * 试卷说明
     */
    @Schema(description = "试卷说明")    
    @TableField(value = "description")
    @NotBlank(message = "试卷标题不能为空",groups = {AddGroup.class})
    private String description;

    /**
     * 试卷设置JSON
     */
    @Schema(description = "试卷设置JSON")    
    @TableField(value = "settings")
    private String settings;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")    
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createTime;


}
