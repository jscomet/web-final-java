package homework.web.entity.dto;

import homework.web.entity.po.Discussion;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 讨论区(Discussion)查询参数
 *
 * @author jscomet
 * @since 2024-12-02 21:54:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DiscussionQuery extends Discussion {
}
