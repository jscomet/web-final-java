package homework.web.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serial;
import java.io.Serializable;

/**
 * 试卷题目关联表(TestQuestion)实体类
 *
 * @author jscomet
 * @since 2024-12-02 22:50:53
 */
@Data
public class TestQuestion implements Serializable {
    @Serial
    private static final long serialVersionUID = -25720748362574032L;
    /**
     * 试卷ID
     */
    @Schema(description = "试卷ID")
    private Long testId;

    /**
     * 题目ID
     */
    @Schema(description = "题目ID")
    private Long questionId;


    /**
     * 题目顺序
     */
    @Schema(description = "题目顺序")    
    @TableField(value = "sort_order")
    private Integer sortOrder;


}
