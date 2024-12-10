package homework.web.entity.vo;

import homework.web.entity.po.TestRecord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自测试卷详情视图模型，携带学生记录信息
 *
 * @author 30597
 * @since 2024-12-10 3:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SelfTestWithRecordVO extends SelfTestVO{
    @Schema(description = "学生ID")
    private Long studentId;
    @Schema(description = "学生信息")
    private UserVO student;
    @Schema(description = "测试记录id")
    private Long recordId;
    @Schema(description = "测试状态 0 未完成 1 已完成")
    private TestRecord.Status recordStatus;
    @Schema(description = "测试记录")
    private TestRecord record;

}
