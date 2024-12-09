package homework.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.context.CurrentUserContext;
import homework.web.dao.TestRecordDao;
import homework.web.entity.dto.TestRecordCommitParam;
import homework.web.entity.po.QuestionBank;
import homework.web.entity.po.TestRecord;
import homework.web.entity.vo.CourseVO;
import homework.web.entity.vo.SelfTestVO;
import homework.web.entity.vo.UserVO;
import homework.web.service.*;
import homework.web.entity.dto.TestRecordQuery;
import homework.web.entity.vo.TestRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.Context;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 考试记录(TestRecord)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 22:51:14
 */
@Service("testRecordService")
public class TestRecordServiceImpl extends ServiceImpl<TestRecordDao, TestRecord> implements TestRecordService {
    @Resource
    private TestRecordDao testRecordDao;
    @Resource
    private CourseEnrollmentService courseEnrollmentService;
    @Autowired
    private QuestionBankService questionBankService;
    @Autowired
    private SelfTestService selfTestService;
    @Resource
    private CourseService courseService;

    @Override
    public TestRecordVO queryById(Long recordId) {
        TestRecordVO vo = testRecordDao.queryById(recordId);
        fillVO(vo);
        return vo;
    }

    @Override
    public List<TestRecordVO> queryAll(int current, int pageSize, TestRecordQuery param) {
        if(current > 0 && pageSize > 0) {
            PageHelper.startPage(current, pageSize);
        }
        List<TestRecordVO> list= testRecordDao.queryAll(param);
        list.forEach(this::fillVO);
        return list;
    }

    private void fillVO(TestRecordVO vo) {
        if (vo == null) {
            return;
        }
        if(vo.getTestId() != null) {
            SelfTestVO selfTestVO = selfTestService.queryById(vo.getTestId());
            vo.setTest(selfTestVO);
            vo.setTitle(Optional.ofNullable(selfTestVO.getTitle()).orElseGet(()->""));
        }
        if(vo.getCourseId()!=null){
            CourseVO courseVO = courseService.queryById(vo.getCourseId());
            vo.setCourse(courseVO);
        }
    }

    @Override
    public int count(TestRecordQuery param) {
        return testRecordDao.count(param);
    }

    @Override
    @Transactional
    public boolean create(Long courseId, Long testId) {

        // 获取课程的学生信息信息
        List<Long> studentIds = courseEnrollmentService.getStudentIdsByCourseId(courseId);
        if (studentIds.isEmpty()) {
            return false;
        }
        List<TestRecord> records = studentIds.stream().map(studentId -> {
            TestRecord record = new TestRecord();
            record.setTestId(testId);
            record.setStudentId(studentId);
            record.setStatus(TestRecord.Status.UNFINISHED);
            return record;
        }).toList();
        return this.saveBatch(records);
    }

    @Override
    public boolean commit(TestRecordCommitParam trCommit) {
        // 获得试卷id对应的试卷
        SelfTestVO selfTestVO = selfTestService.queryById(trCommit.getTestId());
        if (selfTestVO == null) {
            return false;
        }
        // 获得试卷id对应的试卷中的题目数目
        int questionCount = selfTestVO.getQuestions().size();
        int lose = 0;
        int win = 0;
        // 逐个比较selfTestVO中question和trCommit中的questionId相同答案是否相同
        for (TestRecordCommitParam.Answer answer : trCommit.getAnswers()) {
            // Fetch the question from the database using questionId
            QuestionBank question = questionBankService.getById(answer.getQuestionId());
            if (question == null || !question.getCorrectAnswer().equals(answer.getAnswer())) {
                lose++;
                continue;
            }
            win++;
        }
        // 构建数据写入
        TestRecord testRecord = new TestRecord();
        testRecord.setTestId(trCommit.getTestId());
        float i = (float) (win * 100.0 / questionCount);
        testRecord.setScore(i);
        testRecord.setStatus(TestRecord.Status.FINISHED);
        // 把trCommit中的答案转变成json写入
        testRecord.setAnswers(JSON.toJSONString(trCommit.getAnswers()));
        testRecord.setCompleteTime(LocalDateTime.now());
        testRecord.setCourseId(trCommit.getCourseId());
        // 从上下文中获取当前用户id
        UserVO currentUser = CurrentUserContext.getCurrentUser();
        testRecord.setStudentId(currentUser.getUserId());
        return this.save(testRecord);
    }

    @Override
    public TestRecord getTestRecordByTestIdAndStudentId(Long testId, Long studentId) {
        return this.lambdaQuery().eq(TestRecord::getTestId, testId).eq(TestRecord::getStudentId, studentId).one();
    }
}

