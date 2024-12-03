package homework.web.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.TestRecordDao;
import homework.web.entity.dto.TestRecordCommitParam;
import homework.web.entity.po.TestRecord;
import homework.web.service.CourseEnrollmentService;
import homework.web.service.TestRecordService;
import homework.web.entity.dto.TestRecordQuery;
import homework.web.entity.vo.TestRecordVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Override
    public TestRecordVO queryById(Long recordId) {
        return testRecordDao.queryById(recordId);
    }

    @Override
    public List<TestRecordVO> queryAll(int current, int pageSize, TestRecordQuery param) {
        PageHelper.startPage(current, pageSize);
        return testRecordDao.queryAll(param);
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
    //TODO 提交答案，计算分数
    @Override
    public boolean commit(TestRecordCommitParam answer) {

        return false;
    }
}

