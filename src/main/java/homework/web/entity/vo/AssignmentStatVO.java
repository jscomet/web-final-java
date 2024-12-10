package homework.web.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 作业统计(Assignment)视图模型
 *
 * @author jscomet
 * @since 2024-12-02 21:53:13
 */
@Data
public class AssignmentStatVO  {
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
