package homework.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.web.entity.po.CourseEnrollment;
import homework.web.entity.dto.CourseEnrollmentQuery;
import homework.web.entity.vo.CourseEnrollmentVO;

import java.util.List;

/**
 * 课程注册(CourseEnrollment)表服务接口
 *
 * @author jscomet
 * @since 2024-12-02 21:54:11
 */
public interface CourseEnrollmentService extends IService<CourseEnrollment> {
    /**
     * 通过ID查询单条数据
     *
     * @param enrollmentId 主键
     * @return 实例对象
     */
    CourseEnrollmentVO queryById(Long enrollmentId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<CourseEnrollmentVO> queryAll(int current, int pageSize, CourseEnrollmentQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(CourseEnrollmentQuery param);
    /**
     * 学生添加课程注册
     * @param courseId 课程ID
     * @return 是否添加成功
     */
    boolean addByCourseId(Long courseId);
    /**
     * 根据课程id获取学生id列表
     * @param courseId 课程id
     * @return 学生id列表
     */
    List<Long> getStudentIdsByCourseId(Long courseId);
}

