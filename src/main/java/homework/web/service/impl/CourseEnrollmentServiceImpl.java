package homework.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.CourseEnrollmentDao;
import homework.web.entity.po.Course;
import homework.web.entity.po.CourseEnrollment;
import homework.web.entity.vo.CourseVO;
import homework.web.entity.vo.UserVO;
import homework.web.service.CourseEnrollmentService;
import homework.web.entity.dto.CourseEnrollmentQuery;
import homework.web.entity.po.CourseEnrollment;
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
        if(current>0 && pageSize>0) {
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
        if(vo.getCourseId() != null) {
            CourseVO course = courseService.queryById(vo.getCourseId());
            vo.setCourse(course);
        }
        if(vo.getStudentId() != null) {
            UserVO student = userService.queryById(vo.getStudentId());
            userService.desensitize(student);
            vo.setStudent(student);
        }
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
         this.save(insertParam);

         // 更新课程人数
        courseService.lambdaUpdate()
                .eq(Course::getCourseId, courseId)
                .set(Course::getStudentCount, course.getStudentCount() + 1)
                .update();
         return true;
    }

    @Override
    @Transactional
    public boolean quit(Long studentId, Long id) {
        // 查询课程注册信息
        CourseEnrollment courseEnrollment = this.queryById(id);
        AssertUtils.notNull(courseEnrollment, HttpStatus.NOT_FOUND, "课程注册信息不存在");
        AssertUtils.isTrue(Objects.equals(AuthUtils.getCurrentUserId(), courseEnrollment.getStudentId()), HttpStatus.FORBIDDEN,"无权限操作");

        // 课程状态异常
        AssertUtils.isTrue(
                List.of(CourseEnrollment.Status.UNSTART,CourseEnrollment.Status.ONGOING).contains(courseEnrollment.getStatus())
                , HttpStatus.BAD_REQUEST, "已完成或以退出的课程不能退课");

        //更新课程状态
        LambdaUpdateWrapper<CourseEnrollment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CourseEnrollment::getEnrollmentId, id).set(CourseEnrollment::getStatus, CourseEnrollment.Status.QUIT);
        this.update(updateWrapper);
        // 更新课程人数
        Course course = courseService.getById(courseEnrollment.getCourseId());
        // 课程不存在
        AssertUtils.notNull(course, HttpStatus.NOT_FOUND, "课程不存在");
        // 更新课程人数
        courseService.lambdaUpdate()
                .eq(Course::getCourseId, course.getCourseId())
                .set(Course::getStudentCount, course.getStudentCount() - 1)
                .update();
        return true;
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

