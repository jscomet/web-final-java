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
 * 课程轮播图(CourseCarousel)实体类
 *
 * @author jscomet
 * @since 2024-12-02 21:53:50
 */
@Data
public class CourseCarousel implements Serializable {
    @Serial
    private static final long serialVersionUID = -58436512326546566L;
    /**
     * 轮播图ID
     */
    @Schema(description = "轮播图ID")    
    @TableId(value = "carousel_id",type = IdType.AUTO)
    private Long carouselId;


    /**
     * 课程ID
     */
    @Schema(description = "课程ID")    
    @TableField(value = "course_id")
    private Long courseId;

    /**
     * 图片URL
     */
    @Schema(description = "图片URL")    
    @TableField(value = "image_url")
    private String imageUrl;

    /**
     * 排序顺序
     */
    @Schema(description = "排序顺序")    
    @TableField(value = "sort_order")
    private Integer sortOrder;

    /**
     * 是否启用
     */
    @Schema(description = "是否启用")    
    @TableField(value = "status")
    private Boolean status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")    
    @TableField(value = "create_time")
    private LocalDateTime createTime;


}
