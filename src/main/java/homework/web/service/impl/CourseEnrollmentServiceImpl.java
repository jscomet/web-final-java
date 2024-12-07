package homework.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.CourseEnrollmentDao;
import homework.web.entity.po.Course;
import homework.web.entity.po.CourseEnrollment;
import homework.web.service.CourseEnrollmentService;
import homework.web.entity.dto.CourseEnrollmentQuery;
import homework.web.entity.po.CourseEnrollment;
import homework.web.entity.vo.CourseEnrollmentVO;
import homework.web.service.CourseService;
import homework.web.util.AssertUtils;
import homework.web.util.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 课程注册(CourseEnrollment)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 21:54:12
 */
@Service("courseEnrollmentService")
public class CourseEnrollmentServiceImpl extends ServiceImpl<CourseEnrollmentDao, CourseEnrollment> implements CourseEnrollmentService {
    @Resource
    private CourseEnrollmentDao courseEnrollmentDao;
    @Resource
    private CourseService courseService;

    @Override
    public CourseEnrollmentVO queryById(Long enrollmentId) {
        return courseEnrollmentDao.queryById(enrollmentId);
    }

    @Override
    public List<CourseEnrollmentVO> queryAll(int current, int pageSize, CourseEnrollmentQuery param) {
        PageHelper.startPage(current, pageSize);
        return courseEnrollmentDao.queryAll(param);
    }

    @Override
    public int count(CourseEnrollmentQuery param) {
        return courseEnrollmentDao.count(param);
    }

    @Override
    @Transactional
    public boolean addByCourseId(Long courseId) {
        // 检查课程是否存在
        Course course = courseService.getById(courseId);
        AssertUtils.notNull(course, HttpStatus.NOT_FOUND, "课程不存在");
        // 检查学生是否已经注册
        Long studentId = AuthUtils.getCurrentUserId();
        CourseEnrollment enrollment = this.lambdaQuery().eq(CourseEnrollment::getCourseId, courseId)
                .eq(CourseEnrollment::getStudentId, studentId).one();
        AssertUtils.isNull(enrollment, HttpStatus.BAD_REQUEST, "已经注册过该课程");
        // 添加注册信息
        CourseEnrollment insertParam = new CourseEnrollment();
        insertParam.setCourseId(courseId);
        insertParam.setStudentId(studentId);
        return this.save(enrollment);
    }

    @Override
    public List<Long> getStudentIdsByCourseId(Long courseId) {
        return this.lambdaQuery().eq(CourseEnrollment::getCourseId, courseId)
                .list()
                .stream()
                .map(CourseEnrollment::getStudentId)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Long> getCourseIdsByStudentId(Long studentId) {
        return this.lambdaQuery().eq(CourseEnrollment::getStudentId, studentId).list()
                .stream()
                .map(CourseEnrollment::getCourseId)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

