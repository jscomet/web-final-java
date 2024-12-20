package homework.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.CourseEnrollmentDao;
import homework.web.entity.po.Course;
import homework.web.entity.po.CourseEnrollment;
import homework.web.entity.vo.CourseVO;
import homework.web.entity.vo.UserVO;
import homework.web.service.CourseEnrollmentService;
import homework.web.entity.dto.CourseEnrollmentQuery;
import homework.web.entity.vo.CourseEnrollmentVO;
import homework.web.service.CourseService;
import homework.web.service.UserService;
import homework.web.util.AssertUtils;
import homework.web.util.AuthUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    @Resource
    private UserService userService;

    @Override
    public CourseEnrollmentVO queryById(Long enrollmentId) {
        CourseEnrollmentVO vo = courseEnrollmentDao.queryById(enrollmentId);
        this.fillVO(vo);
        return vo;
    }

    @Override
    public List<CourseEnrollmentVO> queryAll(int current, int pageSize, CourseEnrollmentQuery param) {
        if (current >= 0 && pageSize >= 0) {
            PageHelper.startPage(current, pageSize);
        }
        List<CourseEnrollmentVO> list = courseEnrollmentDao.queryAll(param);
        list.forEach(this::fillVO);
        return list;
    }

    @Override
    public int count(CourseEnrollmentQuery param) {
        return courseEnrollmentDao.count(param);
    }

    private void fillVO(CourseEnrollmentVO vo) {
        if (vo == null) {
            return;
        }
        if (vo.getCourseId() != null) {
            CourseVO course = courseService.queryById(vo.getCourseId());
            vo.setCourse(course);
        }
        if (vo.getStudentId() != null) {
            UserVO student = userService.queryById(vo.getStudentId());
            userService.desensitize(student);
            vo.setStudent(student);
        }
    }

    @Override
    @Transactional
    public boolean addByCourseId(Long studentId, Long courseId) {
        // 检查课程是否存在
        Course course = courseService.getById(courseId);
        AssertUtils.notNull(course, HttpStatus.NOT_FOUND, "课程不存在");

        // 检查学生是否已经注册
        CourseEnrollment enrollment = this.lambdaQuery().eq(CourseEnrollment::getCourseId, courseId)
                .eq(CourseEnrollment::getStudentId, studentId).one();
        AssertUtils.isTrue(enrollment == null || !CourseEnrollment.Status.SELECTED.equals(enrollment.getStatus()),
                HttpStatus.BAD_REQUEST, "已经注册过该课程");
        // 添加注册信息
        CourseEnrollment insertParam = new CourseEnrollment();
        insertParam.setEnrollmentId(enrollment == null ? null : enrollment.getEnrollmentId());
        insertParam.setStatus(CourseEnrollment.Status.SELECTED);
        insertParam.setCourseId(courseId);
        insertParam.setStudentId(studentId);
        super.saveOrUpdate(insertParam);

        // 更新课程人数
        courseService.lambdaUpdate()
                .eq(Course::getCourseId, courseId)
                .set(Course::getStudentCount, course.getStudentCount() + 1)
                .update();
        return true;
    }

    @Override
    @Transactional
    public boolean quit(Long studentId, Long courseId) {
        // 查询课程注册信息
        CourseEnrollment courseEnrollment = this.lambdaQuery()
                .eq(CourseEnrollment::getCourseId, courseId)
                .eq(CourseEnrollment::getStudentId, studentId).one();
        AssertUtils.notNull(courseEnrollment, HttpStatus.NOT_FOUND, "课程注册信息不存在");
        AssertUtils.isTrue(Objects.equals(AuthUtils.getCurrentUserId(), courseEnrollment.getStudentId()), HttpStatus.FORBIDDEN, "无权限操作");

        Course course = courseService.getById(courseEnrollment.getCourseId());
        // 课程不存在
        AssertUtils.notNull(course, HttpStatus.NOT_FOUND, "课程不存在");
        // 课程状态异常
        AssertUtils.isTrue(course.getStatus() != Course.Status.ARCHIVED, HttpStatus.BAD_REQUEST, "课程已归档");

        // 更新课程人数
        courseService.lambdaUpdate()
                .eq(Course::getCourseId, courseEnrollment.getCourseId())
                .set(Course::getStudentCount, course.getStudentCount() - 1)
                .update();
        // 更新课程注册学习
        return super.lambdaUpdate().eq(CourseEnrollment::getEnrollmentId, courseEnrollment.getEnrollmentId())
                .set(CourseEnrollment::getStatus, CourseEnrollment.Status.CANCEL)
                .update();
    }

    @Override
    public boolean checkEnrolled(Long studentId, Long courseId) {
        if (studentId == null || courseId == null) {
            return false;
        }

        return super.lambdaQuery()
                .eq(CourseEnrollment::getStudentId,studentId)
                .eq(CourseEnrollment::getCourseId,courseId)
                .eq(CourseEnrollment::getStatus,CourseEnrollment.Status.SELECTED)
                .exists();
    }

    @Override
    public List<Long> getStudentIdsByCourseId(Long courseId) {
        return this.lambdaQuery().eq(CourseEnrollment::getCourseId, courseId)
                .list()
                .stream()
                .map(CourseEnrollment::getStudentId)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Long> getCourseIdsByStudentId(Long studentId) {
        return this.lambdaQuery().eq(CourseEnrollment::getStudentId, studentId).list()
                .stream()
                .map(CourseEnrollment::getCourseId)
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

