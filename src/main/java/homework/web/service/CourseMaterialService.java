package homework.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.web.entity.po.CourseMaterial;
import homework.web.entity.dto.CourseMaterialQuery;
import homework.web.entity.vo.CourseMaterialVO;

import java.util.List;

/**
 * 课件资料(CourseMaterial)表服务接口
 *
 * @author jscomet
 * @since 2024-12-02 21:54:22
 */
public interface CourseMaterialService extends IService<CourseMaterial> {
    /**
     * 通过ID查询单条数据
     *
     * @param materialId 主键
     * @return 实例对象
     */
    CourseMaterialVO queryById(Long materialId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<CourseMaterialVO> queryAll(int current, int pageSize, CourseMaterialQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(CourseMaterialQuery param);

    /**
     * 通过课程ID查询课件资料
     *
     * @param courseId 课程ID
     * @return 课件资料列表
     */
    List<CourseMaterial> queryByCourseId(Long courseId);

}

