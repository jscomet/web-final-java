package homework.web.dao;

import java.util.List;

import homework.web.entity.dto.AssignmentDetailQuery;
import homework.web.entity.vo.AssignmentDetailVO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import homework.web.entity.po.Assignment;
import homework.web.entity.po.Assignment;
import homework.web.entity.dto.AssignmentQuery;
import homework.web.entity.vo.AssignmentVO;

/**
 * 作业(Assignment)表数据库访问层
 *
 * @author jscomet
 * @since 2024-12-02 21:53:12
 */
@Mapper
public interface AssignmentDao extends BaseMapper<Assignment> {
    /**
     * 通过ID查询单条数据
     *
     * @param assignmentId 主键
     * @return 实例对象
     */
    AssignmentVO queryById(Long assignmentId);

    /**
     * 筛选条件查询
     *
     * @param param 查询参数
     * @return 对象列表
     */
    List<AssignmentVO> queryAll(AssignmentQuery param);

    /**
     * 筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(AssignmentQuery param);
    /**
    * 批量新增数据（MyBatis原生foreach方法）
    *
    * @param entities List<Assignment> 实例对象列表
    * @return 影响行数
    */
    int insertBatch(@Param("entities") List<Assignment> entities);

    /**
    * 批量新增或按主键更新数据（MyBatis原生foreach方法）
    *
    * @param entities List<Assignment> 实例对象列表
    * @return 影响行数
    * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
    */
    int insertOrUpdateBatch(@Param("entities") List<Assignment> entities);

    /**
     * 查询所有作业详情
     * @param param 查询参数
     * @return 对象列表
     */
    List<AssignmentDetailVO> queryAllDetail(AssignmentQuery param);
    /**
     * 查询所有作业详情数量
     * @param param 查询参数
     * @return 数量
     */
    int countDetail(AssignmentDetailQuery param);
}




