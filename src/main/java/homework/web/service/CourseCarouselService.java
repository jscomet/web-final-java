package homework.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.web.entity.po.CourseCarousel;
import homework.web.entity.dto.CourseCarouselQuery;
import homework.web.entity.vo.CourseCarouselVO;

import java.util.List;

/**
 * 课程轮播图(CourseCarousel)表服务接口
 *
 * @author jscomet
 * @since 2024-12-02 21:53:50
 */
public interface CourseCarouselService extends IService<CourseCarousel> {
    /**
     * 通过ID查询单条数据
     *
     * @param carouselId 主键
     * @return 实例对象
     */
    CourseCarouselVO queryById(Long carouselId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<CourseCarouselVO> queryAll(int current, int pageSize, CourseCarouselQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(CourseCarouselQuery param);

}

