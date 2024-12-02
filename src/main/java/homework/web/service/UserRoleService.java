package homework.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.web.entity.po.UserRole;
import homework.web.entity.dto.UserRoleQuery;
import homework.web.entity.vo.UserRoleVO;

import java.util.List;

/**
 * (UserRole)表服务接口
 *
 * @author jscomet
 * @since 2024-12-02 19:37:18
 */
public interface UserRoleService extends IService<UserRole> {
    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    UserRoleVO queryById(Integer userId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<UserRoleVO> queryAll(int current, int pageSize, UserRoleQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(UserRoleQuery param);

}

