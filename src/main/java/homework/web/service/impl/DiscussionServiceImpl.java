package homework.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.constant.enums.RoleType;
import homework.web.dao.DiscussionDao;
import homework.web.entity.po.Discussion;
import homework.web.entity.po.DiscussionReply;
import homework.web.entity.vo.UserVO;
import homework.web.service.CourseService;
import homework.web.service.DiscussionReplyService;
import homework.web.service.DiscussionService;
import homework.web.entity.dto.DiscussionQuery;
import homework.web.entity.vo.DiscussionVO;
import homework.web.service.UserService;
import homework.web.util.AssertUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Resource
    private DiscussionReplyService discussionReplyService;

    @Override
    public DiscussionVO queryById(Long discussionId) {
        DiscussionVO vo = discussionDao.queryById(discussionId);
        this.fillVO(vo);
        return vo;
    }
    @Override
    public int count(DiscussionQuery param) {
        return discussionDao.count(param);
    }

    @Override
    @Transactional
    public boolean removeById(Long discussionId) {
        Discussion discussion=this.queryById(discussionId);
        AssertUtils.notNull(discussion, HttpStatus.NOT_FOUND,"讨论不存在");
        //删除讨论
        discussionReplyService.lambdaUpdate().eq(DiscussionReply::getDiscussionId, discussionId).remove();
        //删除讨论讨论回复
        return super.removeById(discussionId);

    }

    @Override
    public List<DiscussionVO> queryAll(int current, int pageSize, DiscussionQuery param) {
        PageHelper.startPage(current, pageSize);
        List<DiscussionVO> list = discussionDao.queryAll(param);
        list.forEach(this::fillVO);
        return list;
    }

    private void fillVO(DiscussionVO vo) {
        if (vo == null) {
            return;
        }
        if (vo.getCourseId() != null) {
            vo.setCourse(courseService.queryById(vo.getCourseId()));
        }
        if (vo.getUserId() != null) {
            UserVO user = userService.queryById(vo.getUserId());
            userService.desensitize(user);
            vo.setUser(user);
            vo.setIsTeacher(user != null && user.getRoles().stream().anyMatch(role -> RoleType.TEACHER.name().equals(role.getEname())));
        }

    }





}

