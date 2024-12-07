package homework.web.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.AssignmentSubmissionDao;
import homework.web.entity.dto.AssignmentFeedbackParam;
import homework.web.entity.dto.AssignmentSubmissionCommitParam;
import homework.web.entity.po.AssignmentSubmission;
import homework.web.entity.vo.AssignmentSubmitStatVO;
import homework.web.service.AssignmentService;
import homework.web.service.AssignmentSubmissionService;
import homework.web.entity.dto.AssignmentSubmissionQuery;
import homework.web.entity.po.AssignmentSubmission;
import homework.web.entity.vo.AssignmentSubmissionVO;
import homework.web.service.CourseEnrollmentService;
import homework.web.service.UserService;
import homework.web.util.AssertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
/**
 * 作业提交(AssignmentSubmission)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 21:53:25
 */
@Service("assignmentSubmissionService")
public class AssignmentSubmissionServiceImpl extends ServiceImpl<AssignmentSubmissionDao, AssignmentSubmission> implements AssignmentSubmissionService {
    @Resource
    private AssignmentSubmissionDao assignmentSubmissionDao;
    @Resource
    private CourseEnrollmentService courseEnrollmentService;
    @Resource
    private AssignmentService assignmentService;
    @Resource
    private UserService userService;

    @Override
    public AssignmentSubmissionVO queryById(Long submissionId) {
        AssignmentSubmissionVO vo = assignmentSubmissionDao.queryById(submissionId);
        fillVO(vo);
        return vo;
    }

    @Override
    public List<AssignmentSubmissionVO> queryAll(int current, int pageSize, AssignmentSubmissionQuery param) {
        if(current > 0 && pageSize > 0){
            PageHelper.startPage(current, pageSize);
        }
        List<AssignmentSubmissionVO> list = assignmentSubmissionDao.queryAll(param);
        list.forEach(this::fillVO);
        return list;
    }

    private void fillVO(AssignmentSubmissionVO vo){
        if(vo == null){
            return;
        }
        if(vo.getAssignmentId() != null){
            vo.setAssignment(assignmentService.queryById(vo.getAssignmentId()));
        }
        if(vo.getStudentId() != null){
            vo.setStudent(userService.queryById(vo.getStudentId()));
            userService.desensitize(vo.getStudent());
        }

    }

    @Override
    public int count(AssignmentSubmissionQuery param) {
        return assignmentSubmissionDao.count(param);
    }

    @Override
    public AssignmentSubmitStatVO querySubmitStat(AssignmentSubmissionQuery param) {
        AssignmentSubmitStatVO stat = new AssignmentSubmitStatVO();
        param.setStatus(AssignmentSubmission.Status.UNCOMMITTED);
        stat.setUnCommittedCount(this.count(param));
        param.setStatus(AssignmentSubmission.Status.UNCORRECTED);
        stat.setUnCorrectedCount(this.count(param));
        param.setStatus(AssignmentSubmission.Status.CORRECTED);
        stat.setCorrectedCount(this.count(param));
        param.setStatus(null);
        stat.setTotalCount(this.count(param));
        return stat;
    }

    @Override
    @Transactional
    public boolean create(Long courseId, Long assignmentId) {
        // 查询课程学生id
        List<Long> studentIds = courseEnrollmentService.getStudentIdsByCourseId(courseId);
        if(studentIds.isEmpty()){
            return true;
        }
        // 创建作业提交信息
        List<AssignmentSubmission> submissions = studentIds.stream().map(studentId -> {
            AssignmentSubmission submission = new AssignmentSubmission();
            submission.setAssignmentId(assignmentId);
            submission.setStudentId(studentId);
            submission.setStatus(AssignmentSubmission.Status.UNCOMMITTED);
            return submission;
        }).toList();
        // 批量保存
        return this.saveOrUpdateBatch(submissions);
    }

    @Override
    @Transactional
    public boolean commit(Long id, Long studentId, AssignmentSubmissionCommitParam param) {
        // 查询是否已经提交
        AssignmentSubmission submission= this.lambdaQuery()
                .eq(AssignmentSubmission::getAssignmentId, id)
                .eq(AssignmentSubmission::getStudentId, studentId).one();
        AssertUtils.notNull(submission, HttpStatus.NOT_FOUND, "作业提交信息不存在");

        // 查看状态
        AssertUtils.isTrue(submission.getStatus() == AssignmentSubmission.Status.UNCOMMITTED, HttpStatus.BAD_REQUEST, "作业已提交");

        // 作业提交信息
        AssignmentSubmission updateParam = new AssignmentSubmission();
        BeanUtils.copyProperties(param, submission);
        updateParam.setSubmissionId(id);
        updateParam.setStudentId(studentId);
        updateParam.setStatus(AssignmentSubmission.Status.UNCOMMITTED);
        updateParam.setSubmitTime(LocalDateTime.now());
        return this.updateById(updateParam);
    }

    @Override
    @Transactional
    public boolean correct(Long id, Long teacherId, AssignmentFeedbackParam param) {
        // 查询是否已经提交
        AssignmentSubmission submission= this.lambdaQuery()
                .eq(AssignmentSubmission::getSubmissionId, id).one();
        AssertUtils.notNull(submission, HttpStatus.NOT_FOUND, "作业提交信息不存在");

        // 查看状态
        AssertUtils.isTrue(submission.getStatus() == AssignmentSubmission.Status.UNCORRECTED, HttpStatus.BAD_REQUEST, "作业已批改");

        // 作业提交信息
        AssignmentSubmission updateParam = new AssignmentSubmission();
        BeanUtils.copyProperties(param, submission);
        updateParam.setSubmissionId(id);
        updateParam.setStatus(AssignmentSubmission.Status.CORRECTED);
        return this.updateById(updateParam);
    }
}

