package homework.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.web.entity.dto.DiscussionBaseReplyQuery;
import homework.web.entity.po.DiscussionReply;
import homework.web.entity.dto.DiscussionReplyQuery;
import homework.web.entity.vo.DiscussionBaseReplyVO;
import homework.web.entity.vo.DiscussionReplyWithSubVO;

import java.util.List;

/**
 * 讨论区回复表(DiscussionReply)表服务接口
 *
 * @author jscomet
 * @since 2024-12-08 16:07:18
 */
public interface DiscussionReplyService extends IService<DiscussionReply> {
    /**
     * 通过ID查询单条数据
     *
     * @param replyId 主键
     * @return 实例对象
     */
    DiscussionBaseReplyVO queryBaseById(Long replyId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<DiscussionBaseReplyVO> queryAllBase(int current, int pageSize, DiscussionBaseReplyQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int countBase(DiscussionBaseReplyQuery param);

    /**
     * 根据讨论ID或者用户id查询讨论回复，一级评论加上其子评论
     * @param current 查询调试
     * @param pageSize 查询调试
     * @param param 查询参数
     * @return 相应评论区
     */
    List<DiscussionReplyWithSubVO> queryAll(int current, int pageSize, DiscussionReplyQuery param);

    /**
     * 通过实体作为筛选一级评论数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(DiscussionReplyQuery param);
}

