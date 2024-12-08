package homework.web.entity.vo;

import homework.web.entity.po.DiscussionReply;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 讨论区回复表(DiscussionReply)视图模型
 *
 * @author jscomet
 * @since 2024-12-08 16:07:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DiscussionReplyWithSubVO extends DiscussionBaseReplyVO {

    @Schema(description = "子回复内容")
    private List<DiscussionSubReplyVO> subReplies;
}
