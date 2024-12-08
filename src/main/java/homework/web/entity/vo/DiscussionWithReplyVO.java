package homework.web.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Description
 *
 * @author 30597
 * @since 2024-12-08 17:57
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DiscussionWithReplyVO extends DiscussionVO {
    @Schema(description = "回复列表,预览前三条")
    private List<DiscussionReplyWithSubVO> replyList;

    @Schema(description = "回复数")
    private Integer replyCount;
}
