package homework.web.entity.vo;

import homework.web.entity.po.DiscussionReply;
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
public class DiscussionBaseReplyVO extends DiscussionReply {
    @Schema(description = "回复人信息")
    private UserVO user;
    @Schema(description = "是否是老师")
    private Boolean isTeacher;

    @Schema(description = "回复数")
    private Integer replyCount;

}
