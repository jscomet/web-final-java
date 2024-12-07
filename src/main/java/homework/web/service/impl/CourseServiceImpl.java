package homework.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.CourseDao;
import homework.web.entity.po.Course;
import homework.web.entity.vo.UserVO;
import homework.web.service.*;
import homework.web.entity.dto.CourseQuery;
import homework.web.entity.po.Course;
import homework.web.entity.vo.CourseVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;

/**
 * 课程信息(Course)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 21:53:41
 */
@Service("courseService")
public class CourseServiceImpl extends ServiceImpl<CourseDao, Course> implements CourseService {
    @Resource
    private CourseDao courseDao;
    @Resource
    private CourseCategoryService courseCategoryService;
    @Resource
    private UserService userService;

    @Override
    public CourseVO queryById(Long courseId) {
        CourseVO vo = courseDao.queryById(courseId);
        fillVO(vo);
        return vo;
    }

    @Override
    public List<CourseVO> queryAll(int current, int pageSize, CourseQuery param) {
        if(current > 0 && pageSize > 0) {
            PageHelper.startPage(current, pageSize);
        }
        List<CourseVO> vos = courseDao.queryAll(param);
        vos.forEach(this::fillVO);
        return vos;
    }

    private void fillVO(CourseVO vo) {
        if (vo != null) {
            if (vo.getCategoryId() != null) {
                vo.setCategory(courseCategoryService.queryById(vo.getCategoryId()));
            }
            if (vo.getTeacherId() != null) {
                UserVO teacher = userService.queryById(vo.getTeacherId());
                userService.desensitize(teacher);
                vo.setTeacher(teacher);
            }
        }
    }

    @Override
    public int count(CourseQuery param) {
        return courseDao.count(param);
    }
}

