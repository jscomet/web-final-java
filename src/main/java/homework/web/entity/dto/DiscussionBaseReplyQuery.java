package homework.web.entity.dto;

import homework.web.config.valid.QueryGroup;
import homework.web.config.valid.SorterValidated;
import homework.web.entity.po.DiscussionReply;
import homework.web.util.beans.Sorter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 讨论区回复表(DiscussionReply)查询参数
 *
 * @author jscomet
 * @since 2024-12-08 16:07:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DiscussionBaseReplyQuery extends DiscussionReply {
    @SorterValidated(groups = {QueryGroup.class})
    @Schema(description = "可选字段 回复时间:createTime 、回复人数 replayCount ")
    private Sorter sorter;
}
