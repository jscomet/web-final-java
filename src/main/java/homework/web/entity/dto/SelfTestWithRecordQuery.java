package homework.web.entity.dto;

import homework.web.entity.po.TestRecord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自测试卷携带记录查询模型
 *
 * @author 30597
 * @since 2024-12-10 3:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SelfTestWithRecordQuery extends SelfTestQuery{
    @Schema(description = "学生ID")
    private Long studentId;
    @Schema(description = "测试记录id")
    private Long recordId;
    @Schema(description = "测试状态 0 未完成 1 已完成")
    private TestRecord.Status recordStatus;
}
