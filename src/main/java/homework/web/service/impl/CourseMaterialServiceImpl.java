package homework.web.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.CourseMaterialDao;
import homework.web.entity.po.CourseMaterial;
import homework.web.service.CourseMaterialService;
import homework.web.entity.dto.CourseMaterialQuery;
import homework.web.entity.vo.CourseMaterialVO;
import homework.web.service.CourseService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.List;
/**
 * 课件资料(CourseMaterial)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 21:54:22
 */
@Service("courseMaterialService")
public class CourseMaterialServiceImpl extends ServiceImpl<CourseMaterialDao, CourseMaterial> implements CourseMaterialService {
    @Resource
    private CourseMaterialDao courseMaterialDao;
    @Resource
    private CourseService courseService;
    @Override
    public CourseMaterialVO queryById(Long materialId) {
        CourseMaterialVO vo = courseMaterialDao.queryById(materialId);
        fillVO(vo);
        return vo;
    }

    @Override
    public List<CourseMaterialVO> queryAll(int current, int pageSize, CourseMaterialQuery param) {
        if(current > 0 && pageSize > 0) {
            PageHelper.startPage(current, pageSize);
        }
        List<CourseMaterialVO> list = courseMaterialDao.queryAll(param);
        list.forEach(this::fillVO);
        return list;
    }

    private void fillVO(CourseMaterialVO vo){
        if(vo == null){
            return;
        }
        if(vo.getCourseId() != null){
            vo.setCourse(courseService.queryById(vo.getCourseId()));
        }
    }

    @Override
    public int count(CourseMaterialQuery param) {
        return courseMaterialDao.count(param);
    }

    @Override
    public List<CourseMaterial> queryByCourseId(Long courseId) {
      // 使用mp进行查询
      return this.list(new LambdaQueryWrapper<CourseMaterial>().eq(CourseMaterial::getCourseId, courseId));
    }
}

