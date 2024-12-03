package homework.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import homework.web.entity.po.CourseEnrollment;
import homework.web.entity.po.CourseEnrollment;
import homework.web.entity.dto.CourseEnrollmentQuery;
import homework.web.entity.vo.CourseEnrollmentVO;

/**
 * 课程注册(CourseEnrollment)表数据库访问层
 *
 * @author jscomet
 * @since 2024-12-02 21:54:11
 */
@Mapper
public interface CourseEnrollmentDao extends BaseMapper<CourseEnrollment> {
    /**
     * 通过ID查询单条数据
     *
     * @param enrollmentId 主键
     * @return 实例对象
     */
    CourseEnrollmentVO queryById(Long enrollmentId);

    /**
     * 筛选条件查询
     *
     * @param param 查询参数
     * @return 对象列表
     */
    List<CourseEnrollmentVO> queryAll(CourseEnrollmentQuery param);

    /**
     * 筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(CourseEnrollmentQuery param);
    /**
    * 批量新增数据（MyBatis原生foreach方法）
    *
    * @param entities List<CourseEnrollment> 实例对象列表
    * @return 影响行数
    */
    int insertBatch(@Param("entities") List<CourseEnrollment> entities);

    /**
    * 批量新增或按主键更新数据（MyBatis原生foreach方法）
    *
    * @param entities List<CourseEnrollment> 实例对象列表
    * @return 影响行数
    * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
    */
    int insertOrUpdateBatch(@Param("entities") List<CourseEnrollment> entities);

}




