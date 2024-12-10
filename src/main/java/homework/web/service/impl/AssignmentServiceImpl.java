package homework.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.AssignmentDao;
import homework.web.entity.dto.AssignmentDetailQuery;
import homework.web.entity.dto.AssignmentSubmissionQuery;
import homework.web.entity.dto.AssignmentSubmitParam;
import homework.web.entity.po.Assignment;
import homework.web.entity.po.AssignmentSubmission;
import homework.web.entity.vo.AssignmentDetailVO;
import homework.web.entity.vo.AssignmentStatVO;
import homework.web.entity.vo.AssignmentWithStatVO;
import homework.web.service.AssignmentService;
import homework.web.entity.dto.AssignmentQuery;
import homework.web.entity.vo.AssignmentVO;
import homework.web.service.AssignmentSubmissionService;
import homework.web.service.CourseService;
import homework.web.service.UserService;
import homework.web.util.AssertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Resource
    private UserService userService;

    @Override
    public AssignmentVO queryById(Long assignmentId) {
        AssignmentVO vo = assignmentDao.queryById(assignmentId);
        this.fillVO(vo);
        return vo;
    }

    @Override
    public List<AssignmentVO> queryAll(int current, int pageSize, AssignmentQuery param) {
        if (current >= 0 && pageSize >= 0) {
            PageHelper.startPage(current, pageSize);
        }
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
        }
        if (vo.getCourseId() != null) {
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
    public boolean publish(Assignment param) {
        // 创建作业
        this.save(param);

        // 创建作业提交信息
        return assignmentSubmissionService.create(param.getCourseId(), param.getAssignmentId());
    }

    @Override
    public List<AssignmentDetailVO> queryAllDetail(Integer current, Integer pageSize, AssignmentDetailQuery param) {
        if (current >= 0 && pageSize >= 0) {
            PageHelper.startPage(current, pageSize);
        }
        List<AssignmentDetailVO> list = assignmentDao.queryAllDetail(param);
        list.forEach(this::fillDetailVO);
        return list;
    }

    private void fillDetailVO(AssignmentDetailVO detailVO) {
        if (detailVO == null) {
            return;
        }
        // 填充作业提交信息
        this.fillVO(detailVO);
        // 填充学生信息
        if (detailVO.getStudentId() != null) {
            detailVO.setStudent(userService.queryById(detailVO.getStudentId()));
            userService.desensitize(detailVO.getStudent());
        }
        if (detailVO.getSubmissionId() != null) {
            detailVO.setSubmission(assignmentSubmissionService.getById(detailVO.getSubmissionId()));
        }

    }

    @Override
    public int countDetail(AssignmentDetailQuery param) {
        return assignmentDao.countDetail(param);
    }

    @Override
    public boolean submit(Long studentId, AssignmentSubmitParam param) {
        //查询作业信息
        Assignment assignment = super.getById(param.getAssignmentId());
        AssertUtils.notNull(assignment, HttpStatus.NOT_FOUND, "作业不存在");

        // 查询是否已经提交
        AssignmentSubmission old = assignmentSubmissionService.lambdaQuery()
                .eq(AssignmentSubmission::getAssignmentId, param.getAssignmentId())
                .eq(AssignmentSubmission::getStudentId, studentId)
                .one();
        AssertUtils.isTrue(old == null || AssignmentSubmission.Status.UNCOMMITTED.equals(old.getStatus()), HttpStatus.BAD_REQUEST, "已经提交过作业");

        // 提交作业记录
        AssignmentSubmission submission = new AssignmentSubmission();
        BeanUtils.copyProperties(param, submission);
        submission.setSubmissionId(old != null ? old.getSubmissionId() : null);
        submission.setStudentId(studentId);
        submission.setStatus(AssignmentSubmission.Status.UNCORRECTED);
        submission.setSubmitTime(LocalDateTime.now());
        // 保存或更新提交信息
        return assignmentSubmissionService.saveOrUpdate(submission);
    }

    @Override
    public List<AssignmentWithStatVO> queryAllWithStat(Integer current, Integer pageSize, AssignmentDetailQuery param) {
        //拷贝一份查询参数
        AssignmentDetailQuery query=new AssignmentDetailQuery();
        BeanUtils.copyProperties(param,query);
        return this.queryAllDetail(current,pageSize,query).stream()
                .map(assignmentVO -> {
                    AssignmentWithStatVO vo = new AssignmentWithStatVO();
                    BeanUtils.copyProperties(assignmentVO, vo);
                    query.setAssignmentId(assignmentVO.getAssignmentId());
                    vo.setStat(this.queryStat(query));
                    return vo;
                }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public AssignmentStatVO queryStat(AssignmentDetailQuery query) {
        AssignmentStatVO vo=new AssignmentStatVO();
        //查询未提交
        query.setSubmissionStatus(AssignmentSubmission.Status.UNCOMMITTED);
        vo.setUnCommittedCount(this.countDetail(query));
        //查询未批改
        query.setSubmissionStatus(AssignmentSubmission.Status.UNCORRECTED);
        vo.setUnCorrectedCount(this.countDetail(query));
        //查询已批改
        query.setSubmissionStatus(AssignmentSubmission.Status.CORRECTED);
        vo.setCorrectedCount(this.countDetail(query));
        //查询全部
        query.setSubmissionStatus(null);
        vo.setTotalCount(this.countDetail(query));
        return vo;
    }
}

