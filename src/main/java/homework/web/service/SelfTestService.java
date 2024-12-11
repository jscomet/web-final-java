package homework.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.web.entity.dto.SelfTestCreateParam;
import homework.web.entity.dto.SelfTestWithRecordQuery;
import homework.web.entity.dto.TestRecordCommitParam;
import homework.web.entity.po.SelfTest;
import homework.web.entity.dto.SelfTestQuery;
import homework.web.entity.po.TestRecord;
import homework.web.entity.vo.SelfTestVO;
import homework.web.entity.vo.SelfTestWithRecordVO;
import homework.web.entity.vo.TestRecordResultVO;

import java.util.List;

/**
 * 自测试卷(SelfTest)表服务接口
 *
 * @author jscomet
 * @since 2024-12-02 22:51:42
 */
public interface SelfTestService extends IService<SelfTest> {
    /**
     * 通过ID查询单条数据
     *
     * @param testId 主键
     * @return 实例对象
     */
    SelfTestVO queryById(Long testId);

    /**
     * 查询多条数据
     *
     * @param current  查询页面
     * @param pageSize 查询条数
     * @param param    查询参数
     * @return 对象列表
     */
    List<SelfTestVO> queryAll(int current, int pageSize, SelfTestQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(SelfTestQuery param);

    /**
     * 自动生成自测试卷
     *
     * @param param 自测试卷信息
     * @return 自测试卷ID
     */
    Long generateSelfTest(SelfTest param);

    /**
     * 添加自测试卷
     *
     * @param param 自测试卷信息
     * @return 是否添加成功
     */
    boolean create(SelfTestCreateParam param);

    /**
     * 发布试卷到指定课程的学生
     *
     * @param id       主键
     * @param courseId 课程id
     * @return 实例对象
     */
    boolean publish(Long id, Long courseId);
    /**
     * 查询多条数据
     *
     * @param current  查询页面
     * @param pageSize 查询条数
     * @param param    查询参数
     * @return 对象列表
     */
    List<SelfTestWithRecordVO> queryAllWithRecord(Integer current, Integer pageSize, SelfTestWithRecordQuery param);
    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int countWithRecord(SelfTestWithRecordQuery param);

    /**
     * 自动品评测试卷
     * @param testId 测试卷id
     * @param answers 学生提交的答案
     * @return 测试结果
     */
    TestRecordResultVO scoreSelfTest(Long testId,  List<TestRecordCommitParam.Answer> answers);
}

