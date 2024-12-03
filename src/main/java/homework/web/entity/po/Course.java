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
 * 课程信息(Course)实体类
 *
 * @author jscomet
 * @since 2024-12-02 21:53:41
 */
@Data
public class Course implements Serializable {
    @Serial
    private static final long serialVersionUID = -10587005210875369L;
    /**
     * 课程ID
     */
    @Schema(description = "课程ID")    
    @TableId(value = "course_id",type = IdType.AUTO)
    private Long courseId;


    /**
     * 课程标题
     */
    @Schema(description = "课程标题")    
    @TableField(value = "title")
    private String title;

    /**
     * 课程描述
     */
    @Schema(description = "课程描述")    
    @TableField(value = "description")
    private String description;

    /**
     * 分类ID
     */
    @Schema(description = "分类ID")    
    @TableField(value = "category_id")
    private Long categoryId;

    /**
     * 教师ID
     */
    @Schema(description = "教师ID")    
    @TableField(value = "teacher_id")
    private Long teacherId;

    /**
     * 封面图片URL
     */
    @Schema(description = "封面图片URL")    
    @TableField(value = "cover_image")
    private String coverImage;

    /**
     * 是否推荐
     */
    @Schema(description = "是否推荐")    
    @TableField(value = "is_recommended")
    private Boolean isRecommended;

    /**
     * 课程状态：0-草稿、1-已发布、2-已归档
     */
    @Schema(description = "课程状态：0-草稿、1-已发布、2-已归档")    
    @TableField(value = "status")
    private Integer status;

    /**
     * 是否允许评论
     */
    @Schema(description = "是否允许评论")    
    @TableField(value = "allow_comment")
    private Boolean allowComment;

    /**
     * 是否允许笔记
     */
    @Schema(description = "是否允许笔记")    
    @TableField(value = "allow_notes")
    private Boolean allowNotes;

    /**
     * 浏览次数
     */
    @Schema(description = "浏览次数")    
    @TableField(value = "view_count")
    private Integer viewCount;

    /**
     * 点赞数
     */
    @Schema(description = "点赞数")    
    @TableField(value = "like_count")
    private Integer likeCount;

    /**
     * 学生数量
     */
    @Schema(description = "学生数量")    
    @TableField(value = "student_count")
    private Integer studentCount;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")    
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")    
    @TableField(value = "update_time")
    private LocalDateTime updateTime;


}
