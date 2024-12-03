package homework.web.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import homework.web.entity.po.TestRecord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Description
 *
 * @author 30597
 * @since 2024-12-03 14:42
 */
@Data
public class TestRecordCommitParam {

    /**
     * 记录ID
     */
    @Schema(description = "记录ID")
    private Long recordId;
    /**
     * 学生ID
     */
    @Schema(description = "学生ID")
    private Long studentId;

    /**
     * 答案JSON
     */
    @Schema(description = "答案JSON")
    private String answers;
}
