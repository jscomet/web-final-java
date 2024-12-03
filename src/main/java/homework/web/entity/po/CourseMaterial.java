package homework.web.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 课件资料(CourseMaterial)实体类
 *
 * @author jscomet
 * @since 2024-12-02 21:54:22
 */
@Data
public class CourseMaterial implements Serializable {
    @Serial
    private static final long serialVersionUID = -45009774474158296L;
    /**
     * 课件ID
     */
    @Schema(description = "课件ID")    
    @TableId(value = "material_id",type = IdType.AUTO)
    private Long materialId;


    /**
     * 课程ID
     */
    @Schema(description = "课程ID")    
    @TableField(value = "course_id")
    private Long courseId;

    /**
     * 课件标题
     */
    @Schema(description = "课件标题")    
    @TableField(value = "title")
    private String title;

    /**
     * 课件类型：0-文档、1-视频、2-音频、3-图片
     */
    @Schema(description = "课件类型：0-文档、1-视频、2-音频、3-图片")    
    @TableField(value = "type")
    private Integer type;

    /**
     * 内容URL
     */
    @Schema(description = "内容URL")    
    @TableField(value = "content_url")
    private String contentUrl;

    /**
     * 排序顺序
     */
    @Schema(description = "排序顺序")    
    @TableField(value = "sort_order")
    private Integer sortOrder;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")    
    @TableField(value = "create_time")
    private LocalDateTime createTime;


}
