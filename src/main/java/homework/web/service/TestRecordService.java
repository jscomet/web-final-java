package homework.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.web.entity.dto.TestRecordCommitParam;
import homework.web.entity.po.TestRecord;
import homework.web.entity.dto.TestRecordQuery;
import homework.web.entity.vo.TestRecordVO;

import java.util.List;

/**
 * 考试记录(TestRecord)表服务接口
 *
 * @author jscomet
 * @since 2024-12-02 22:51:13
 */
public interface TestRecordService extends IService<TestRecord> {
    /**
     * 通过ID查询单条数据
     *
     * @param recordId 主键
     * @return 实例对象
     */
    TestRecordVO queryById(Long recordId);

    /**
     * 查询多条数据
     *
     * @param current  查询页面
     * @param pageSize 查询条数
     * @param param    查询参数
     * @return 对象列表
     */
    List<TestRecordVO> queryAll(int current, int pageSize, TestRecordQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(TestRecordQuery param);

    /**
     * 根据课程id发布考试记录
     *
     * @param courseId 课程id
     * @param testId   考试id
     * @return 是否发布成功
     */
    boolean create(Long courseId, Long testId);

    /**
     * 学生提交试卷答案
     *
     * @param answer 提交答案
     * @return 是否提交成功
     */
    boolean commit(TestRecordCommitParam answer);

}

