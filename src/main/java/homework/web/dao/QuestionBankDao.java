package homework.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import homework.web.entity.po.QuestionBank;
import homework.web.entity.po.QuestionBank;
import homework.web.entity.dto.QuestionBankQuery;
import homework.web.entity.vo.QuestionBankVO;

/**
 * 题库(QuestionBank)表数据库访问层
 *
 * @author jscomet
 * @since 2024-12-02 22:51:56
 */
@Mapper
public interface QuestionBankDao extends BaseMapper<QuestionBank> {
    /**
     * 通过ID查询单条数据
     *
     * @param questionId 主键
     * @return 实例对象
     */
    QuestionBankVO queryById(Long questionId);

    /**
     * 筛选条件查询
     *
     * @param param 查询参数
     * @return 对象列表
     */
    List<QuestionBankVO> queryAll(QuestionBankQuery param);

    /**
     * 筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(QuestionBankQuery param);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<QuestionBank> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<QuestionBank> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<QuestionBank> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<QuestionBank> entities);
    /**
     * 通过试卷ID获取试卷的题目类型
     * @param testId 试卷ID
     * @return 题目类型列表
     */
    List<Integer> selectQuestionTypesByTestId(@Param("testId") Long testId);
}




