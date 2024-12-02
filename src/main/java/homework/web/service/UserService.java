package homework.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.web.entity.po.User;
import homework.web.entity.dto.UserQuery;
import homework.web.entity.vo.UserVO;

import java.util.List;

/**
 * 用户(User)表服务接口
 *
 * @author jscomet
 * @since 2024-12-02 19:36:59
 */
public interface UserService extends IService<User> {
    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    UserVO queryById(Long userId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<UserVO> queryAll(int current, int pageSize, UserQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(UserQuery param);

}

