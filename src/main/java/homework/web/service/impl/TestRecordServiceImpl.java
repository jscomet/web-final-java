package homework.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.context.CurrentUserContext;
import homework.web.dao.TestRecordDao;
import homework.web.entity.dto.TestRecordCommitParam;
import homework.web.entity.po.QuestionBank;
import homework.web.entity.po.TestRecord;
import homework.web.entity.vo.*;
import homework.web.service.*;
import homework.web.entity.dto.TestRecordQuery;
import homework.web.util.AIHelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        if(current >= 0 && pageSize >= 0) {
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
    public TestRecordResultVO commit(Long studentId,TestRecordCommitParam trCommit) {
        // 获得试卷id对应的试卷
        SelfTestVO selfTestVO = selfTestService.queryById(trCommit.getTestId());
        if (selfTestVO == null) {
            return null;
        }

        //构建测试结果
        TestRecordResultVO resultVO =selfTestService.scoreSelfTest(trCommit.getTestId(), trCommit.getAnswers());

        //查询测试记录
        TestRecord old=this.lambdaQuery().eq(TestRecord::getStudentId,studentId)
                .eq(TestRecord::getTestId,trCommit.getTestId()).one();


        //插入测试数据
        TestRecord testRecord=new TestRecord();
        testRecord.setAnswers(trCommit.getAnswers());
        testRecord.setMaxScore(resultVO.getMaxScore());
        testRecord.setCorrectAnswers(resultVO.getCorrectAnswers());
        testRecord.setQuestionScore(resultVO.getQuestionScore());
        testRecord.setScore(resultVO.getTotalScore());
        testRecord.setStatus(TestRecord.Status.FINISHED);
        testRecord.setTestId(trCommit.getTestId());
        testRecord.setStudentId(studentId);
        testRecord.setCourseId(selfTestVO.getCourseId());
        if(old !=null){
            testRecord.setRecordId(old.getRecordId());
            testRecord.setScore(old.getScore()>testRecord.getScore()?old.getScore():testRecord.getScore());
        }
        //插入或更新
        super.saveOrUpdate(testRecord);

        //返回测评结构
        return resultVO;
    }

    @Override
    public TestRecord getTestRecordByTestIdAndStudentId(Long testId, Long studentId) {
        return this.lambdaQuery().eq(TestRecord::getTestId, testId).eq(TestRecord::getStudentId, studentId).one();
    }
}

