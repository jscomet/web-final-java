package homework.web.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.CourseCategoryDao;
import homework.web.entity.po.CourseCategory;
import homework.web.service.CourseCategoryService;
import homework.web.entity.dto.CourseCategoryQuery;
import homework.web.entity.po.CourseCategory;
import homework.web.entity.vo.CourseCategoryVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;
/**
 * 课程分类(CourseCategory)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 21:54:00
 */
@Service("courseCategoryService")
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryDao, CourseCategory> implements CourseCategoryService {
    @Resource
    private CourseCategoryDao courseCategoryDao;

    @Override
    public CourseCategoryVO queryById(Long categoryId) {
        return courseCategoryDao.queryById(categoryId);
    }

    @Override
    public List<CourseCategoryVO> queryAll(int current, int pageSize, CourseCategoryQuery param) {
        PageHelper.startPage(current, pageSize);
        return courseCategoryDao.queryAll(param);
    }

    @Override
    public int count(CourseCategoryQuery param) {
        return courseCategoryDao.count(param);
    }
}

