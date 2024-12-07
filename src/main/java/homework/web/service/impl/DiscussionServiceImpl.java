package homework.web.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.DiscussionDao;
import homework.web.entity.po.Discussion;
import homework.web.entity.vo.UserVO;
import homework.web.service.CourseService;
import homework.web.service.DiscussionService;
import homework.web.entity.dto.DiscussionQuery;
import homework.web.entity.vo.DiscussionVO;
import homework.web.service.UserService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.ArrayList;
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
    @Resource
    private CourseService courseService;
    @Resource
    private UserService userService;

    @Override
    public DiscussionVO queryById(Long discussionId) {
        DiscussionVO vo = discussionDao.queryById(discussionId);
        this.fillVO(vo);
        return vo;
    }

    @Override
    public List<DiscussionVO> queryAll(int current, int pageSize, DiscussionQuery param) {
        PageHelper.startPage(current, pageSize);
        List<DiscussionVO> list = discussionDao.queryAll(param);
        list.forEach(this::fillVO);
        return list;
    }

    private void fillVO(DiscussionVO vo) {
        if(vo == null) {
            return;
        }
        if(vo.getCourseId() != null) {
            vo.setCourse(courseService.queryById(vo.getCourseId()));
        }
        if(vo.getUserId() != null) {
            UserVO user = userService.queryById(vo.getUserId());
            userService.desensitize(user);
            vo.setUser(user);
        }

    }
    // 将讨论区列表转换为树形结构
    @Override
    public List<DiscussionVO> convertToTree(List<DiscussionVO> list) {
        // 保存根节点
        List<DiscussionVO> root = new ArrayList<>();
        // 保存所有节点
        List<DiscussionVO> all = new ArrayList<>();
        // 将所有节点保存到all中
        all.addAll(list);
        // 遍历所有节点
        for (DiscussionVO discussion : list) {
            // 如果当前节点的父节点id为0，则说明当前节点是根节点
            if (discussion.getParentId() == 0) {
                root.add(discussion);
            }
            // 遍历所有节点，将当前节点添加到父节点的children中
            for (DiscussionVO d : all) {
                if (d.getParentId() != null && d.getParentId().equals(discussion.getDiscussionId())) {
                    if (discussion.getChildren() == null) {
                        discussion.setChildren(new ArrayList<>());
                    }
                    discussion.getChildren().add(d);
                }
            }
        }
        return root;
    }



    @Override
    public int count(DiscussionQuery param) {
        return discussionDao.count(param);
    }


}

