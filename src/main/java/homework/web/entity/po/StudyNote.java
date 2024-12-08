package homework.web.entity.po;

import homework.web.config.valid.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学习笔记(StudyNote)实体类
 *
 * @author jscomet
 * @since 2024-12-02 22:51:31
 */
@Data
public class StudyNote implements Serializable {
    @Serial
    private static final long serialVersionUID = -27314413558103324L;
    /**
     * 笔记ID
     */
    @Schema(description = "笔记ID")    
    @TableId(value = "note_id",type = IdType.AUTO)
    private Long noteId;


    /**
     * 课程ID
     */
    @Schema(description = "课程ID")    
    @TableField(value = "course_id")
    @Null(groups = {UpdateGroup.class}, message = "课程ID不可修改")
    private Long courseId;

    /**
     * 学生ID
     */
    @Schema(description = "学生ID")    
    @TableField(value = "student_id")
    @Null(groups = {UpdateGroup.class}, message = "课程ID不可修改")
    private Long studentId;

    /**
     * 标题
     */
    @Schema(description = "标题")
    @TableField(value = "title")
    private String title;

    /**
     * 笔记内容
     */
    @Schema(description = "笔记内容")    
    @TableField(value = "content")
    private String content;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")    
    @TableField(value = "create_time")
    private LocalDateTime createTime;


}
