package homework.web.entity.vo;

import homework.web.entity.po.Assignment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 作业提交情况统计视图模型
 *
 * @author 30597
 * @since 2024-12-03 12:07
 */
@Data
public class AssignmentSubmitStatVO  {
    /**
     * 未提交次数
     */
    @Schema(description = "未提交次数")
    private Integer unCommittedCount;

    /**
     * 未批改人数
     */
    @Schema(description = "未批改次数")
    private Integer unCorrectedCount;

    /**
     * 已批改人数
     */
    @Schema(description = "已批改次数")
    private Integer correctedCount;

    /**
     * 总人数
     */
    @Schema(description = "总次数")
    private Integer totalCount;


}
