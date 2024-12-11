package homework.web.service;

import homework.web.entity.po.DiscussionReply;
import homework.web.util.AIHelperUtils;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {
    
    @Resource
    private DiscussionReplyService discussionReplyService;
    
    private static final Long CHAT_ROBOT_ACCOUNT = 40L;
    
    @Async
    public void asyncAIReply(Long discussionId, String content) {
        DiscussionReply discussionReply = new DiscussionReply();
        discussionReply.setDiscussionId(discussionId);
        discussionReply.setParentId(0L);
        String replayContent = AIHelperUtils.replay(content);
        discussionReply.setUserId(CHAT_ROBOT_ACCOUNT);
        discussionReply.setContent(replayContent);
        discussionReply.setLikeCount(0);
        discussionReplyService.save(discussionReply);
    }

    @Async
    public void asyncAIReplyDiscussion(Long discussionId,Long parentId, String content) {
        DiscussionReply discussionReply = new DiscussionReply();
        discussionReply.setDiscussionId(discussionId);
        String replayContent = AIHelperUtils.replay(content);
        discussionReply.setUserId(CHAT_ROBOT_ACCOUNT);
        discussionReply.setParentId(parentId);
        discussionReply.setContent(replayContent);
        discussionReply.setLikeCount(0);
        discussionReplyService.save(discussionReply);
    }
} 