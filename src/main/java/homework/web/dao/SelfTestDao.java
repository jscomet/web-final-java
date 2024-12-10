package homework.web.dao;

import java.util.List;

import homework.web.entity.dto.SelfTestWithRecordQuery;
import homework.web.entity.vo.SelfTestWithRecordVO;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import homework.web.entity.po.SelfTest;
import homework.web.entity.po.SelfTest;
import homework.web.entity.dto.SelfTestQuery;
import homework.web.entity.vo.SelfTestVO;

/**
 * 自测试卷(SelfTest)表数据库访问层
 *
 * @author jscomet
 * @since 2024-12-02 22:51:41
 */
@Mapper
public interface SelfTestDao extends BaseMapper<SelfTest> {
    /**
     * 通过ID查询单条数据
     *
     * @param testId 主键
     * @return 实例对象
     */
    SelfTestVO queryById(Long testId);

    /**
     * 筛选条件查询
     *
     * @param param 查询参数
     * @return 对象列表
     */
    List<SelfTestVO> queryAll(SelfTestQuery param);

    /**
     * 筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(SelfTestQuery param);
    /**
    * 批量新增数据（MyBatis原生foreach方法）
    *
    * @param entities List<SelfTest> 实例对象列表
    * @return 影响行数
    */
    int insertBatch(@Param("entities") List<SelfTest> entities);

    /**
    * 批量新增或按主键更新数据（MyBatis原生foreach方法）
    *
    * @param entities List<SelfTest> 实例对象列表
    * @return 影响行数
    * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
    */
    int insertOrUpdateBatch(@Param("entities") List<SelfTest> entities);

    /**
     * 查询所有自测试卷及其记录
     * @param param 查询参数
     * @return 对象列表
     */
    List<SelfTestWithRecordVO> queryAllWithRecord(SelfTestWithRecordQuery param);

    /**
     * 查询所有自测试卷及其记录数量
     * @param param 查询参数
     * @return 数量
     */
    int countWithRecord(SelfTestWithRecordQuery param);
}




