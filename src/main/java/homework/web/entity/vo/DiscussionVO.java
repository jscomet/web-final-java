package homework.web.entity.vo;

import homework.web.entity.po.Discussion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 讨论区(Discussion)视图模型
 *
 * @author jscomet
 * @since 2024-12-02 21:54:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DiscussionVO extends Discussion {
    @Schema(description = "子评论")
    private List<Discussion> children;
}
