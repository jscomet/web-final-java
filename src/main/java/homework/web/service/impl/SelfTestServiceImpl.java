package homework.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.SelfTestDao;
import homework.web.entity.dto.SelfTestCreateParam;
import homework.web.entity.dto.SelfTestWithRecordQuery;
import homework.web.entity.po.*;
import homework.web.entity.vo.SelfTestWithRecordVO;
import homework.web.service.*;
import homework.web.entity.dto.SelfTestQuery;
import homework.web.entity.po.SelfTest;
import homework.web.entity.vo.SelfTestVO;
import homework.web.util.AssertUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    private void fillWithRecordVO(SelfTestWithRecordVO vo) {
        if (vo == null) {
            return;
        }
        this.fillVO(vo);
        if(vo.getRecordId() != null){
            vo.setRecord(testRecordService.getById(vo.getRecordId()));
        }
        if(vo.getStudentId()!=null){
            vo.setStudent(userService.queryById(vo.getStudentId()));
            userService.desensitize(vo.getStudent());
        }

    }

}

