package homework.web.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.QuestionBankDao;
import homework.web.entity.po.QuestionBank;
import homework.web.entity.po.TestQuestion;
import homework.web.service.QuestionBankService;
import homework.web.entity.dto.QuestionBankQuery;
import homework.web.entity.po.QuestionBank;
import homework.web.entity.vo.QuestionBankVO;
import homework.web.service.TestQuestionService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 题库(QuestionBank)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 22:51:57
 */
@Service("questionBankService")
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankDao, QuestionBank> implements QuestionBankService {
    @Resource
    private QuestionBankDao questionBankDao;
    @Resource
    private TestQuestionService testQuestionService;

    @Override
    public QuestionBankVO queryById(Long questionId) {
        return questionBankDao.queryById(questionId);
    }

    @Override
    public List<QuestionBankVO> queryAll(int current, int pageSize, QuestionBankQuery param) {
        PageHelper.startPage(current, pageSize);
        return questionBankDao.queryAll(param);
    }

    @Override
    public int count(QuestionBankQuery param) {
        return questionBankDao.count(param);
    }

    @Override
    public List<QuestionBank> getQuestionIdsByCourseId(int id) {
//        使用mybatis-plus的查询这个课程id的所有题目
        return questionBankDao.selectList(new QueryWrapper<QuestionBank>().eq("course_id", id));
    }

    @Override
    public List<QuestionBank> getQuestionsByTestId(Long testId) {
        List<Long> questionIds = testQuestionService.lambdaQuery()
                .eq(TestQuestion::getTestId, testId)
                .select(TestQuestion::getQuestionId).list().stream()
                .map(TestQuestion::getQuestionId).toList();
        if (questionIds.isEmpty()) {
            return new ArrayList<>();
        }
        // 通过 MyBatis-Plus 的 Mapper 查询试卷的题目
        return this.listByIds(questionIds);
    }

    @Override
    public List<String> getQuestionTypesByTestId(Long testId) {
        List<Long> questionIds = testQuestionService.lambdaQuery()
                .eq(TestQuestion::getTestId, testId)
                .select(TestQuestion::getQuestionId).list().stream()
                .map(TestQuestion::getQuestionId).toList();
        if(questionIds.isEmpty()){
            return new ArrayList<>();
        }
        // 通过 MyBatis-Plus 的 Mapper 查询试卷的题目类型
        return this.lambdaQuery()
                .select(QuestionBank::getType)
                .in(QuestionBank::getQuestionId, questionIds)
                .list().stream()
                .map(QuestionBank::getType)
                .distinct()
                .map(QuestionBank.Type::valueOf)
                .filter(Objects::nonNull)
                .map(QuestionBank.Type::getDesc)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

