package homework.web.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.CourseCarouselDao;
import homework.web.entity.po.CourseCarousel;
import homework.web.service.CourseCarouselService;
import homework.web.entity.dto.CourseCarouselQuery;
import homework.web.entity.po.CourseCarousel;
import homework.web.entity.vo.CourseCarouselVO;
import homework.web.service.CourseService;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;
/**
 * 课程轮播图(CourseCarousel)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 21:53:50
 */
@Service("courseCarouselService")
public class CourseCarouselServiceImpl extends ServiceImpl<CourseCarouselDao, CourseCarousel> implements CourseCarouselService {
    @Resource
    private CourseCarouselDao courseCarouselDao;
    @Resource
    private CourseService courseService;

    @Override
    public CourseCarouselVO queryById(Long carouselId) {
        CourseCarouselVO vo = courseCarouselDao.queryById(carouselId);
        fillVO(vo);
        return vo;
    }

    @Override
    public List<CourseCarouselVO> queryAll(int current, int pageSize, CourseCarouselQuery param) {
        if(current > 0 && pageSize > 0) {
            PageHelper.startPage(current, pageSize);
        }
        List<CourseCarouselVO> list = courseCarouselDao.queryAll(param);
        list.forEach(this::fillVO);
        return list;
    }

    private void fillVO(CourseCarouselVO vo){
        if(vo == null){
            return;
        }
        if(vo.getCourseId() != null){
            vo.setCourse(courseService.queryById(vo.getCourseId()));
        }
    }

    @Override
    public int count(CourseCarouselQuery param) {
        return courseCarouselDao.count(param);
    }
}

