package homework.web.entity.dto;

import homework.web.config.valid.QueryGroup;
import homework.web.config.valid.SorterValidated;
import homework.web.entity.po.Discussion;
import homework.web.util.beans.Sorter;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @SorterValidated(groups = {QueryGroup.class})
    @Schema(description = "可选字段 回复时间:createTime 、回复人数 replayCount 点赞数 likeCount")
    private Sorter sorter;
}
