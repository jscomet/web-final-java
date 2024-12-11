package homework.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.SelfTestDao;
import homework.web.entity.dto.SelfTestCreateParam;
import homework.web.entity.dto.SelfTestWithRecordQuery;
import homework.web.entity.dto.TestRecordCommitParam;
import homework.web.entity.po.*;
import homework.web.entity.vo.SelfTestWithRecordVO;
import homework.web.entity.vo.TestRecordResultVO;
import homework.web.service.*;
import homework.web.entity.dto.SelfTestQuery;
import homework.web.entity.po.SelfTest;
import homework.web.entity.vo.SelfTestVO;
import homework.web.util.AIHelperUtils;
import homework.web.util.AssertUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 自测试卷(SelfTest)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 22:51:42
 */
@Service("selfTestService")
public class SelfTestServiceImpl extends ServiceImpl<SelfTestDao, SelfTest> implements SelfTestService {
    @Resource
    private SelfTestDao selfTestDao;
    @Resource
    private TestQuestionService testQuestionService;
    @Resource
    private QuestionBankService questionBankService;
    @Resource
    private CourseEnrollmentService courseEnrollmentService;
    @Resource
    private TestRecordService testRecordService;
    @Resource
    private CourseService courseService;
    @Resource
    private UserService userService;

    @Override
    public SelfTestVO queryById(Long testId) {
        SelfTestVO vo = selfTestDao.queryById(testId);
        fillVO(vo);
        return vo;
    }

    @Override
    public List<SelfTestVO> queryAll(int current, int pageSize, SelfTestQuery param) {
        if (current >= 0 && pageSize >= 0) {
            PageHelper.startPage(current, pageSize);
        }
        List<SelfTestVO> vos = selfTestDao.queryAll(param);
        vos.forEach(this::fillVO);
        return vos;
    }

    private void fillVO(SelfTestVO vo) {
        if (vo == null) {
            return;
        }
        if (vo.getTestId() != null) {
            vo.setQuestionTypes(questionBankService.getQuestionTypesByTestId(vo.getTestId()));

            vo.setQuestionCount(testQuestionService.lambdaQuery().eq(TestQuestion::getTestId, vo.getTestId()).count());
            vo.setQuestions(questionBankService.getQuestionsByTestId(vo.getTestId()));
        }
        if (vo.getCourseId() != null) {
            vo.setCourse(courseService.queryById(vo.getCourseId()));
        }
    }

    @Override
    public int count(SelfTestQuery param) {
        return selfTestDao.count(param);
    }

    @Override
    public Long generateSelfTest(SelfTest param) {
        //TODO 自动生成自测试卷
        return 0L;
    }

    @Override
    public boolean create(SelfTestCreateParam param) {
        // 插入自测试卷
        this.save(param);
        Long testId = param.getTestId();
        // 插入自测试卷题目
        if (CollectionUtils.isNotEmpty(param.getQuestionIds())) {
            int index = 0;
            for (Long questionId : param.getQuestionIds()) {
                TestQuestion testQuestion = new TestQuestion();
                testQuestion.setTestId(testId);
                testQuestion.setQuestionId(questionId);
                testQuestion.setSortOrder(index++);
                testQuestionService.save(testQuestion);
            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean publish(Long id, Long courseId) {
        SelfTest selfTest = this.getById(id);
        AssertUtils.notNull(selfTest, HttpStatus.NOT_FOUND, "自测试卷不存在");
        return testRecordService.create(courseId, id);

    }

    @Override
    public List<SelfTestWithRecordVO> queryAllWithRecord(Integer current, Integer pageSize, SelfTestWithRecordQuery param) {
        if (current >= 0 && pageSize >= 0) {
            PageHelper.startPage(current, pageSize);
        }
        List<SelfTestWithRecordVO> list = selfTestDao.queryAllWithRecord(param);
        list.forEach(this::fillWithRecordVO);
        return list;
    }

    @Override
    public int countWithRecord(SelfTestWithRecordQuery param) {
        return selfTestDao.countWithRecord(param);
    }

    @Override
    public TestRecordResultVO scoreSelfTest(Long testId, List<TestRecordCommitParam.Answer> answers) {
        List<QuestionBank> questionBanks = questionBankService.getQuestionsByTestId(testId);
        if (CollectionUtils.isEmpty(questionBanks)) {
            return null;
        }
        Map<Long, QuestionBank> questionBankMap = questionBanks.stream().collect(Collectors.toMap(QuestionBank::getQuestionId, questionBank -> questionBank));

        TestRecordResultVO result = new TestRecordResultVO();
        Map<Long, String> answerMap = answers.stream().collect(Collectors.toMap(TestRecordCommitParam.Answer::getQuestionId, TestRecordCommitParam.Answer::getAnswer));
        Map<Long, String> correctAnswerMap = questionBanks.stream().collect(Collectors.toMap(QuestionBank::getQuestionId, QuestionBank::getCorrectAnswer));
        Map<Long, Float> scoreMap = questionBanks.stream().collect(Collectors.toMap(QuestionBank::getQuestionId, (questionBank) -> 0f));
        float totalScore = 0;
        float maxScore = 100.0f;
        //每一道题的分值
        float questionScore = maxScore / questionBanks.size();
        for (Map.Entry<Long, String> entry : answerMap.entrySet()) {
            Long questionId = entry.getKey();
            String studentAnswer = entry.getValue();
            QuestionBank question = questionBankMap.get(questionId);
            if (question == null) {
                continue;
            }
            boolean isCorrect = false;
            switch (question.getType()) {
                case 0: // 单选题
                case 2: // 判断题
                    if (studentAnswer != null && studentAnswer.equals(question.getCorrectAnswer())) {
                        isCorrect = true;
                    }
                    break;

                case 1: // 多选题
                    //将问题的答案序列话未
                    String[] correctAnswers = question.getCorrectAnswer().split(",");       // 正确答案
                    String[] studentAnswers = studentAnswer.split(",");                   // 学生答案
                    HashSet<String> correctSet = Arrays.stream(correctAnswers).map(String::trim).map(String::toLowerCase).collect(Collectors.toCollection(HashSet::new));
                    HashSet<String> studentSet = Arrays.stream(studentAnswers).map(String::trim).map(String::toLowerCase).collect(Collectors.toCollection(HashSet::new));
                    //使用set来判断两个数组是否相等
                    if (correctAnswers.length == studentAnswers.length && correctSet.equals(studentSet)) {
                        isCorrect = true;
                    }
                    break;
                case 3: // 填空题
                    if (studentAnswer != null && studentAnswer.equals(question.getCorrectAnswer())) {
                        isCorrect = true;
                    }
                    break;

                case 4: // 问答题
                    // 调用 LLM 来评分并传递学生答案与正确答案
                    String aiFeedback = AIHelperUtils.aiAnalyse("学生答案: " + studentAnswer + " 正确答案: " + question.getCorrectAnswer());

                    // 这里可以根据反馈来决定是否给予分数
                    if (aiFeedback.contains("正确")) {
                        isCorrect = true;
                    }
                    String correctAnswer = question.getCorrectAnswer() + "\nAI反馈结果:" + aiFeedback;
                    correctAnswerMap.put(questionId, correctAnswer);
                    break;

                default:
                    break;
            }

            scoreMap.put(questionId, isCorrect ? questionScore : 0);
            totalScore += isCorrect ? questionScore : 0;
        }

        result.setTotalScore(totalScore);
        result.setMaxScore( maxScore);
        result.setQuestionScore(scoreMap);
        result.setCorrectAnswers(correctAnswerMap);
        return result;
    }

    private void fillWithRecordVO(SelfTestWithRecordVO vo) {
        if (vo == null) {
            return;
        }
        this.fillVO(vo);
        if (vo.getRecordId() != null) {
            vo.setRecord(testRecordService.getById(vo.getRecordId()));
        }
        if (vo.getStudentId() != null) {
            vo.setStudent(userService.queryById(vo.getStudentId()));
            userService.desensitize(vo.getStudent());
        }

    }

}

