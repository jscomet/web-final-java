package homework.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.web.entity.po.CourseCategory;
import homework.web.entity.dto.CourseCategoryQuery;
import homework.web.entity.vo.CourseCategoryVO;

import java.util.List;

/**
 * 课程分类(CourseCategory)表服务接口
 *
 * @author jscomet
 * @since 2024-12-02 21:54:00
 */
public interface CourseCategoryService extends IService<CourseCategory> {
    /**
     * 通过ID查询单条数据
     *
     * @param categoryId 主键
     * @return 实例对象
     */
    CourseCategoryVO queryById(Long categoryId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<CourseCategoryVO> queryAll(int current, int pageSize, CourseCategoryQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(CourseCategoryQuery param);

}

