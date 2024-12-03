package homework.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.TestQuestionDao;
import homework.web.entity.po.TestQuestion;
import homework.web.service.TestQuestionService;
import homework.web.entity.dto.TestQuestionQuery;
import homework.web.entity.vo.TestQuestionVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;

/**
 * 试卷题目关联表(TestQuestion)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 22:50:53
 */
@Service("testQuestionService")
public class TestQuestionServiceImpl extends ServiceImpl<TestQuestionDao, TestQuestion> implements TestQuestionService {
    @Resource
    private TestQuestionDao testQuestionDao;

    @Override
    public TestQuestionVO queryById(Long testId) {
        return testQuestionDao.queryById(testId);
    }

    @Override
    public List<TestQuestionVO> queryAll(int current, int pageSize, TestQuestionQuery param) {
        if (current > 0 && pageSize > 0) {
            PageHelper.startPage(current, pageSize);
        }
        return testQuestionDao.queryAll(param);
    }

    @Override
    public int count(TestQuestionQuery param) {
        return testQuestionDao.count(param);
    }

    @Override
    public List<Long> queryQuestionIdsByTestId(Long testId) {
        TestQuestionQuery query = new TestQuestionQuery();
        query.setTestId(testId);
        List<TestQuestionVO> testQuestions = this.queryAll(-1, -1, query);
        return testQuestions.stream().map(TestQuestion::getQuestionId).toList();

    }
}

