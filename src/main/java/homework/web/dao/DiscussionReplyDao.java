package homework.web.dao;

import java.util.List;

import homework.web.entity.dto.DiscussionBaseReplyQuery;
import homework.web.entity.vo.DiscussionBaseReplyVO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import homework.web.entity.po.DiscussionReply;

/**
 * 讨论区回复表(DiscussionReply)表数据库访问层
 *
 * @author jscomet
 * @since 2024-12-08 16:07:18
 */
@Mapper
public interface DiscussionReplyDao extends BaseMapper<DiscussionReply> {
    /**
     * 通过ID查询单条数据
     *
     * @param replyId 主键
     * @return 实例对象
     */
    DiscussionBaseReplyVO queryBaseById(Long replyId);

    /**
     * 筛选条件查询
     *
     * @param param 查询参数
     * @return 对象列表
     */
    List<DiscussionBaseReplyVO> queryAllBase(DiscussionBaseReplyQuery param);

    /**
     * 筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int countBase(DiscussionBaseReplyQuery param);
    /**
    * 批量新增数据（MyBatis原生foreach方法）
    *
    * @param entities List<DiscussionReply> 实例对象列表
    * @return 影响行数
    */
    int insertBatch(@Param("entities") List<DiscussionReply> entities);

    /**
    * 批量新增或按主键更新数据（MyBatis原生foreach方法）
    *
    * @param entities List<DiscussionReply> 实例对象列表
    * @return 影响行数
    * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
    */
    int insertOrUpdateBatch(@Param("entities") List<DiscussionReply> entities);
}




