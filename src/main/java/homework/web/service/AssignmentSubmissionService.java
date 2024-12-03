package homework.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.web.entity.dto.AssignmentFeedbackParam;
import homework.web.entity.dto.AssignmentSubmissionCommitParam;
import homework.web.entity.po.AssignmentSubmission;
import homework.web.entity.dto.AssignmentSubmissionQuery;
import homework.web.entity.vo.AssignmentSubmissionVO;
import homework.web.entity.vo.AssignmentSubmitStatVO;

import java.util.List;

/**
 * 作业提交(AssignmentSubmission)表服务接口
 *
 * @author jscomet
 * @since 2024-12-02 21:53:25
 */
public interface AssignmentSubmissionService extends IService<AssignmentSubmission> {
    /**
     * 通过ID查询单条数据
     *
     * @param submissionId 主键
     * @return 实例对象
     */
    AssignmentSubmissionVO queryById(Long submissionId);

    /**
     * 查询多条数据
     *
     * @param current  查询页面
     * @param pageSize 查询条数
     * @param param    查询参数
     * @return 对象列表
     */
    List<AssignmentSubmissionVO> queryAll(int current, int pageSize, AssignmentSubmissionQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(AssignmentSubmissionQuery param);

    /**
     * 获取作业的提交统计
     *
     * @param param 提交参数
     * @return 是否成功
     */
    AssignmentSubmitStatVO querySubmitStat(AssignmentSubmissionQuery param);

    /**
     * 根据课程id和作业id新增作业提交
     *
     * @param courseId     课程id
     * @param assignmentId 作业id
     * @return 是否成功
     */
    boolean create(Long courseId, Long assignmentId);

    /**
     * 提交作业
     *
     * @param id        作业ID
     * @param studentId 学生ID
     * @param param     提交参数
     * @return 是否成功
     */
    boolean commit(Long id, Long studentId, AssignmentSubmissionCommitParam param);

    /**
     * 教师批改作业
     *
     * @param id        作业ID
     * @param teacherId 教师ID
     * @param param     评分和反馈信息
     * @return 是否成功
     */
    boolean correct(Long id, Long teacherId, AssignmentFeedbackParam param);
}

