package homework.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import homework.web.entity.po.User;
import homework.web.entity.po.User;
import homework.web.entity.dto.UserQuery;
import homework.web.entity.vo.UserVO;

/**
 * 用户(User)表数据库访问层
 *
 * @author jscomet
 * @since 2024-12-02 19:36:58
 */
@Mapper
public interface UserDao extends BaseMapper<User> {
    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    UserVO queryById(Long userId);

    /**
     * 筛选条件查询
     *
     * @param param 查询参数
     * @return 对象列表
     */
    List<UserVO> queryAll(UserQuery param);

    /**
     * 筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(UserQuery param);
    /**
    * 批量新增数据（MyBatis原生foreach方法）
    *
    * @param entities List<User> 实例对象列表
    * @return 影响行数
    */
    int insertBatch(@Param("entities") List<User> entities);

    /**
    * 批量新增或按主键更新数据（MyBatis原生foreach方法）
    *
    * @param entities List<User> 实例对象列表
    * @return 影响行数
    * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
    */
    int insertOrUpdateBatch(@Param("entities") List<User> entities);

}




