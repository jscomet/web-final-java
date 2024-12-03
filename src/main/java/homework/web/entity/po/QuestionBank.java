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
 * 题库(QuestionBank)实体类
 *
 * @author jscomet
 * @since 2024-12-02 22:51:57
 */
@Data
public class QuestionBank implements Serializable {
    @Serial
    private static final long serialVersionUID = 118776737108585246L;
    /**
     * 题目ID
     */
    @Schema(description = "题目ID")    
    @TableId(value = "question_id",type = IdType.AUTO)
    private Long questionId;


    /**
     * 创建者ID
     */
    @Schema(description = "创建者ID")    
    @TableField(value = "creator_id")
    private Long creatorId;

    /**
     * 题目类型：0-单选、1-多选、2-判断、3-填空、4-问答
     */
    @Schema(description = "题目类型：0-单选、1-多选、2-判断、3-填空、4-问答")    
    @TableField(value = "type")
    private Integer type;

    /**
     * 题目内容
     */
    @Schema(description = "题目内容")    
    @TableField(value = "question_text")
    private String questionText;

    /**
     * 选项JSON
     */
    @Schema(description = "选项JSON")    
    @TableField(value = "options")
    private String options;

    /**
     * 正确答案
     */
    @Schema(description = "正确答案")    
    @TableField(value = "correct_answer")
    private String correctAnswer;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")    
    @TableField(value = "create_time")
    private LocalDateTime createTime;


}
