package homework.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.web.entity.po.TestQuestion;
import homework.web.entity.dto.TestQuestionQuery;
import homework.web.entity.vo.TestQuestionVO;

import java.util.List;

/**
 * 试卷题目关联表(TestQuestion)表服务接口
 *
 * @author jscomet
 * @since 2024-12-02 22:50:53
 */
public interface TestQuestionService extends IService<TestQuestion> {
    /**
     * 通过ID查询单条数据
     *
     * @param testId 主键
     * @return 实例对象
     */
    TestQuestionVO queryById(Long testId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<TestQuestionVO> queryAll(int current, int pageSize, TestQuestionQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(TestQuestionQuery param);
    /**
     * 通过试卷ID查询题目ID
     *
     * @param testId 试卷ID
     * @return 题目ID列表
     */
    List<Long> queryQuestionIdsByTestId(Long testId);
}

