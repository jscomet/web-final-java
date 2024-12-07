package homework.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.web.entity.po.Discussion;
import homework.web.entity.dto.DiscussionQuery;
import homework.web.entity.vo.DiscussionVO;

import java.util.List;

/**
 * 讨论区(Discussion)表服务接口
 *
 * @author jscomet
 * @since 2024-12-02 21:54:31
 */
public interface DiscussionService extends IService<Discussion> {
    /**
     * 通过ID查询单条数据
     *
     * @param discussionId 主键
     * @return 实例对象
     */
    DiscussionVO queryById(Long discussionId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<DiscussionVO> queryAll(int current, int pageSize, DiscussionQuery param);

    /**
     * 将讨论区列表转换为树形结构
     *
     * @param list 讨论区列表
     * @return 树形结构
     */
    List<DiscussionVO> convertToTree(List<DiscussionVO> list);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(DiscussionQuery param);
}

