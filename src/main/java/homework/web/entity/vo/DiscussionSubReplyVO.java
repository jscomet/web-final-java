package homework.web.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 讨论区回复表(DiscussionReply)视图模型
 *
 * @author jscomet
 * @since 2024-12-08 16:07:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DiscussionSubReplyVO extends DiscussionBaseReplyVO {
        @Schema(description = "向谁回复,@XX")
        private UserVO replyTo;
}
