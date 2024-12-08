package homework.web.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 讨论区回复表(DiscussionReply)实体类
 *
 * @author jscomet
 * @since 2024-12-08 16:07:18
 */
@Data
public class DiscussionReply implements Serializable {
    @Serial
    private static final long serialVersionUID = -84561958966259056L;
    /**
     * 回复ID
     */
    @Schema(description = "回复ID")    
    @TableId(value = "reply_id",type = IdType.AUTO)
    private Long replyId;


    /**
     * 讨论ID
     */
    @Schema(description = "讨论ID")    
    @TableField(value = "discussion_id")
    private Long discussionId;

    /**
     * 回复人id
     */
    @Schema(description = "回复人id")    
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 回复内容
     */
    @Schema(description = "回复内容")    
    @TableField(value = "content")
    private String content;

    /**
     * 父回复ID,如果为无则为一级回复
     */
    @Schema(description = "父回复ID,如果为无则为一级回复")    
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 点赞数
     */
    @Schema(description = "点赞数")    
    @TableField(value = "like_count")
    private Integer likeCount;

    /**
     * 回复时间
     */
    @Schema(description = "回复时间")    
    @TableField(value = "create_time")
    private LocalDateTime createTime;


}
