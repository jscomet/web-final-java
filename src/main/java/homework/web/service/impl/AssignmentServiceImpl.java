package homework.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.AssignmentDao;
import homework.web.entity.dto.AssignmentSubmissionQuery;
import homework.web.entity.po.Assignment;
import homework.web.entity.po.AssignmentSubmission;
import homework.web.entity.vo.AssignmentSubmitStatVO;
import homework.web.service.AssignmentService;
import homework.web.entity.dto.AssignmentQuery;
import homework.web.entity.po.Assignment;
import homework.web.entity.vo.AssignmentVO;
import homework.web.service.AssignmentSubmissionService;
import homework.web.service.CourseService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 作业(Assignment)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 21:53:13
 */
@Service("assignmentService")
public class AssignmentServiceImpl extends ServiceImpl<AssignmentDao, Assignment> implements AssignmentService {
    @Resource
    private AssignmentDao assignmentDao;
    @Resource
    private AssignmentSubmissionService assignmentSubmissionService;
    @Resource
    private CourseService courseService;

    @Override
    public AssignmentVO queryById(Long assignmentId) {
        AssignmentVO vo = assignmentDao.queryById(assignmentId);
        this.fillVO(vo);
        return vo;
    }

    @Override
    public List<AssignmentVO> queryAll(int current, int pageSize, AssignmentQuery param) {
        PageHelper.startPage(current, pageSize);
        List<AssignmentVO> list = assignmentDao.queryAll(param);
        list.forEach(this::fillVO);
        return list;
    }

    private void fillVO(AssignmentVO vo) {
        if (vo == null) {
            return;
        }
        if (vo.getAssignmentId() != null) {
            // 填充作业提交信息
            AssignmentSubmissionQuery param = new AssignmentSubmissionQuery();
            param.setAssignmentId(vo.getAssignmentId());
            // 获取作业提交统计
            AssignmentSubmitStatVO stat = assignmentSubmissionService.querySubmitStat(param);
            vo.setSubmitStat(stat);
        }
        if(vo.getCourseId() != null){
            // 填充课程信息
            vo.setCourse(courseService.queryById(vo.getCourseId()));
        }
    }

    @Override
    public int count(AssignmentQuery param) {
        return assignmentDao.count(param);
    }

    @Override
    @Transactional
    public boolean removeById(Long assignmentId) {
        // 删除作业
        assignmentSubmissionService.lambdaUpdate()
                .eq(AssignmentSubmission::getAssignmentId, assignmentId)
                .remove();
        return super.removeById(assignmentId);
    }

    @Override
    @Transactional
    public boolean publish( Assignment param) {
        // 创建作业
        this.save(param);

        // 创建作业提交信息
        return assignmentSubmissionService.create(param.getCourseId(), param.getAssignmentId());
    }
}

