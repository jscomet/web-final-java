package homework.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import homework.web.entity.po.SystemConfig;
import homework.web.entity.po.SystemConfig;
import homework.web.entity.dto.SystemConfigQuery;
import homework.web.entity.vo.SystemConfigVO;

/**
 * 系统配置(SystemConfig)表数据库访问层
 *
 * @author jscomet
 * @since 2024-12-07 19:27:25
 */
@Mapper
public interface SystemConfigDao extends BaseMapper<SystemConfig> {
    /**
     * 通过ID查询单条数据
     *
     * @param name 主键
     * @return 实例对象
     */
    SystemConfigVO queryById(String name);

    /**
     * 筛选条件查询
     *
     * @param param 查询参数
     * @return 对象列表
     */
    List<SystemConfigVO> queryAll(SystemConfigQuery param);

    /**
     * 筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(SystemConfigQuery param);
    /**
    * 批量新增数据（MyBatis原生foreach方法）
    *
    * @param entities List<SystemConfig> 实例对象列表
    * @return 影响行数
    */
    int insertBatch(@Param("entities") List<SystemConfig> entities);

    /**
    * 批量新增或按主键更新数据（MyBatis原生foreach方法）
    *
    * @param entities List<SystemConfig> 实例对象列表
    * @return 影响行数
    * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
    */
    int insertOrUpdateBatch(@Param("entities") List<SystemConfig> entities);

}




