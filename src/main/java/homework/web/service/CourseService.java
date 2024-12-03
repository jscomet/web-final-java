package homework.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.web.entity.po.Course;
import homework.web.entity.dto.CourseQuery;
import homework.web.entity.vo.CourseVO;

import java.util.List;

/**
 * 课程信息(Course)表服务接口
 *
 * @author jscomet
 * @since 2024-12-02 21:53:41
 */
public interface CourseService extends IService<Course> {
    /**
     * 通过ID查询单条数据
     *
     * @param courseId 主键
     * @return 实例对象
     */
    CourseVO queryById(Long courseId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<CourseVO> queryAll(int current, int pageSize, CourseQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(CourseQuery param);

}

