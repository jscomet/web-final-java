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
 * 课程分类(CourseCategory)实体类
 *
 * @author jscomet
 * @since 2024-12-02 21:54:00
 */
@Data
public class CourseCategory implements Serializable {
    @Serial
    private static final long serialVersionUID = -35622055574764218L;
    /**
     * 分类ID
     */
    @Schema(description = "分类ID")    
    @TableId(value = "category_id",type = IdType.AUTO)
    private Long categoryId;


    /**
     * 分类名称
     */
    @Schema(description = "分类名称")    
    @TableField(value = "name")
    private String name;

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
