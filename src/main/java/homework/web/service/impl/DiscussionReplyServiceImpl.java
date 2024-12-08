package homework.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.constant.enums.RoleType;
import homework.web.dao.DiscussionReplyDao;
import homework.web.entity.dto.DiscussionBaseReplyQuery;
import homework.web.entity.po.DiscussionReply;
import homework.web.entity.vo.DiscussionBaseReplyVO;
import homework.web.entity.vo.DiscussionReplyWithSubVO;
import homework.web.entity.vo.DiscussionSubReplyVO;
import homework.web.entity.vo.UserVO;
import homework.web.service.DiscussionReplyService;
import homework.web.entity.dto.DiscussionReplyQuery;
import homework.web.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * 讨论区回复表(DiscussionReply)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-08 16:07:18
 */
@Service("discussionReplyService")
public class DiscussionReplyServiceImpl extends ServiceImpl<DiscussionReplyDao, DiscussionReply> implements DiscussionReplyService {
    @Resource
    private DiscussionReplyDao discussionReplyDao;
    @Resource
    private UserService userService;

    @Override
    public DiscussionBaseReplyVO queryBaseById(Long replyId) {
        DiscussionBaseReplyVO vo = discussionReplyDao.queryBaseById(replyId);
        this.fillVO(vo);
        return vo;
    }

    @Override
    public List<DiscussionBaseReplyVO> queryAllBase(int current, int pageSize, DiscussionBaseReplyQuery param) {
        if (current > 0 && pageSize > 0) {
            PageHelper.startPage(current, pageSize);
        }
        List<DiscussionBaseReplyVO> list = discussionReplyDao.queryAllBase(param);
        list.forEach(this::fillVO);
        return list;
    }

    private void fillVO(DiscussionBaseReplyVO vo) {
        if (vo == null) {
            return;
        }
        if (vo.getUserId() != null) {
            UserVO user = userService.queryById(vo.getUserId());
            vo.setUser(userService.queryById(vo.getUserId()));
            userService.desensitize(vo.getUser());
            vo.setIsTeacher(user != null && user.getRoles().stream().anyMatch(role -> RoleType.TEACHER.name().equals(role.getEname())));
        }
    }

    @Override
    public int countBase(DiscussionBaseReplyQuery param) {
        return discussionReplyDao.countBase(param);
    }

    @Override
    public List<DiscussionReplyWithSubVO> queryAll(int current, int pageSize, DiscussionReplyQuery param) {
        //获取所有评论评论
        DiscussionBaseReplyQuery baseParam = new DiscussionBaseReplyQuery();
        BeanUtils.copyProperties(param, baseParam);
        List<DiscussionBaseReplyVO> all = this.queryAllBase(current, pageSize, baseParam);
        //根据一级评论获取在器项目的子评论
        List<DiscussionReplyWithSubVO> root = new ArrayList<>();
        for (DiscussionBaseReplyVO base : all) {
            if (base.getParentId() == null || base.getParentId() == 0) {
                DiscussionReplyWithSubVO vo = new DiscussionReplyWithSubVO();
                BeanUtils.copyProperties(base, vo);
                root.add(vo);
            }
        }
        //存在多级子评论，递归获取
        for (DiscussionReplyWithSubVO vo : root) {
            vo.setSubReplies(this.getSubReplies(vo, all));
        }
        return root;
    }



    // 根据第一级评论，获取在一级评论下的子评论及其子评论
    private List<DiscussionSubReplyVO> getSubReplies(DiscussionReplyWithSubVO root, List<DiscussionBaseReplyVO> all) {
        List<DiscussionSubReplyVO> subReplies = new ArrayList<>();
        // 根据bfs查询子评论
        List<DiscussionBaseReplyVO> queue = new ArrayList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            // 从队列中移除第一个元素
            DiscussionBaseReplyVO base = queue.remove(0);
            for (DiscussionBaseReplyVO b : all) {
                // 检查当前评论是否是base评论的子评论
                if (b.getParentId() != null && b.getParentId().equals(base.getReplyId())) {
                    DiscussionSubReplyVO vo = new DiscussionSubReplyVO();
                    // 复制属性到子评论视图对象
                    BeanUtils.copyProperties(b, vo);
                    vo.setReplyTo(base.getUser());
                    subReplies.add(vo);
                    // 将子评论添加到队列中以继续搜索其子评论
                    queue.add(b);
                }
            }
        }
        return subReplies;
    }
    @Override
    public int count(DiscussionReplyQuery param) {
        DiscussionBaseReplyQuery baseParam = new DiscussionBaseReplyQuery();
        BeanUtils.copyProperties(param, baseParam);
        // 一级评论的父评论id为0
        baseParam.setParentId(0L);
        return this.countBase(baseParam);
    }


}

