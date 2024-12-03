package homework.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import homework.web.entity.po.CourseMaterial;
import homework.web.entity.po.CourseMaterial;
import homework.web.entity.dto.CourseMaterialQuery;
import homework.web.entity.vo.CourseMaterialVO;

/**
 * 课件资料(CourseMaterial)表数据库访问层
 *
 * @author jscomet
 * @since 2024-12-02 21:54:22
 */
@Mapper
public interface CourseMaterialDao extends BaseMapper<CourseMaterial> {
    /**
     * 通过ID查询单条数据
     *
     * @param materialId 主键
     * @return 实例对象
     */
    CourseMaterialVO queryById(Long materialId);

    /**
     * 筛选条件查询
     *
     * @param param 查询参数
     * @return 对象列表
     */
    List<CourseMaterialVO> queryAll(CourseMaterialQuery param);

    /**
     * 筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(CourseMaterialQuery param);
    /**
    * 批量新增数据（MyBatis原生foreach方法）
    *
    * @param entities List<CourseMaterial> 实例对象列表
    * @return 影响行数
    */
    int insertBatch(@Param("entities") List<CourseMaterial> entities);

    /**
    * 批量新增或按主键更新数据（MyBatis原生foreach方法）
    *
    * @param entities List<CourseMaterial> 实例对象列表
    * @return 影响行数
    * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
    */
    int insertOrUpdateBatch(@Param("entities") List<CourseMaterial> entities);

}




