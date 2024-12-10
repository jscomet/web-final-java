package homework.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.web.entity.dto.AssignmentDetailQuery;
import homework.web.entity.dto.AssignmentSubmitParam;
import homework.web.entity.po.Assignment;
import homework.web.entity.dto.AssignmentQuery;
import homework.web.entity.vo.AssignmentDetailVO;
import homework.web.entity.vo.AssignmentStatVO;
import homework.web.entity.vo.AssignmentVO;
import homework.web.entity.vo.AssignmentWithStatVO;

import java.util.List;

/**
 * 作业(Assignment)表服务接口
 *
 * @author jscomet
 * @since 2024-12-02 21:53:12
 */
public interface AssignmentService extends IService<Assignment> {
    /**
     * 通过ID查询单条数据
     *
     * @param assignmentId 主键
     * @return 实例对象
     */
    AssignmentVO queryById(Long assignmentId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<AssignmentVO> queryAll(int current, int pageSize, AssignmentQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(AssignmentQuery param);
    /**
     * 移除数据
     *
     * @param assignmentId 主键
     * @return 实例对象
     */
    boolean removeById(Long assignmentId);
    /**
     * 发布作业
     *
     * @param param 作业参数
     * @return 是否成功
     */
    boolean publish(Assignment param);
    /**
     * 作业提交详情
     *
     * @param param 作业参数
     * @return 是否成功
     */
    List<AssignmentDetailVO> queryAllDetail(Integer current, Integer pageSize, AssignmentDetailQuery param);
    /**
     * 作业提交详情计数
     *
     * @param param 作业参数
     * @return 数量
     */
    int countDetail(AssignmentDetailQuery param);
    /**
     * 提交作业
     *
     * @param studentId 学生ID
     * @param param     作业提交参数
     * @return 是否成功
     */
    boolean submit(Long studentId, AssignmentSubmitParam param);
    /**
     * 查询作业统计情况
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<AssignmentWithStatVO> queryAllWithStat(Integer current, Integer pageSize, AssignmentDetailQuery param);

    /**
     * 查询作业统计情况计数
     *
     * @param param 查询参数
     * @return 数量
     */
    AssignmentStatVO queryStat(AssignmentDetailQuery param);
}

