package homework.web.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
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
@Data
public class DiscussionReplyQuery {
    /**
     * 讨论ID
     */
    @Schema(description = "讨论ID")
    @TableField(value = "discussion_id")
    private Long discussionId;

    @SorterValidated(groups = {QueryGroup.class})
    @Schema(description = "可选字段 回复时间:createTime 、回复人数 replayCount 点赞数 likeCount")
    private Sorter sorter;
}
