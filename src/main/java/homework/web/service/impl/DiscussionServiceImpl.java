package homework.web.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.DiscussionDao;
import homework.web.entity.po.Discussion;
import homework.web.service.DiscussionService;
import homework.web.entity.dto.DiscussionQuery;
import homework.web.entity.po.Discussion;
import homework.web.entity.vo.DiscussionVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;
/**
 * 讨论区(Discussion)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 21:54:31
 */
@Service("discussionService")
public class DiscussionServiceImpl extends ServiceImpl<DiscussionDao, Discussion> implements DiscussionService {
    @Resource
    private DiscussionDao discussionDao;

    @Override
    public DiscussionVO queryById(Long discussionId) {
        return discussionDao.queryById(discussionId);
    }

    @Override
    public List<DiscussionVO> queryAll(int current, int pageSize, DiscussionQuery param) {
        PageHelper.startPage(current, pageSize);
        return discussionDao.queryAll(param);
    }

    public void fillVO(DiscussionVO vo){
        if(vo == null){
            return;
        }
        if(vo.getDiscussionId() == null){
            vo.setChildren(this.lambdaQuery().eq(Discussion::getParentId, vo.getDiscussionId()).list());
        }
    }

    @Override
    public int count(DiscussionQuery param) {
        return discussionDao.count(param);
    }
}

