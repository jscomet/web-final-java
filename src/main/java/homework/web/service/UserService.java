package homework.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.web.entity.dto.*;
import homework.web.entity.po.User;
import homework.web.entity.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 用户(User)表服务接口
 *
 * @author jscomet
 * @since 2024-12-02 20:23:54
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
     * @param current  查询页面
     * @param pageSize 查询条数
     * @param param    查询参数
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

    /**
     * 重置密码
     *
     * @param userId      用户ID
     * @param newPassword 新密码
     * @return 是否成功
     */
    boolean resetPassword(Long userId, String newPassword);

    /**
     * 启用禁用用户
     *
     * @param id 用户
     * @return 是否成功
     */
    boolean enableDisableUser(Long id);

    /**
     * 通过用户名密码登录
     *
     * @param param 登录参数
     * @return token
     */
    String loginByPassword(UserLoginPasswordParam param);

    /**
     * 当局用户退出登录
     */
    void logout();
    /**
     * 修改密码
     *
     * @param param 修改密码参数
     * @return 是否成功
     */
    boolean updatePassword(UserPasswordParam param);

    /**
     * 信息脱敏
     * @param user 用户
     */
    void desensitize(UserVO user);
    /**
     * 通过表单参数更新用户信息
     *
     * @param param 表单参数
     * @return 是否成功
     */
    boolean updateByForm(Long id, UserForm param);
    /**
     * 通过表单参数保存用户信息
     *
     * @param param 表单参数
     * @return 是否成功
     */
    boolean saveByForm(UserForm param);
    /**
     * 通过表单参数注册用户
     *
     * @param param 表单参数
     * @return 是否成功
     */
    String register(UserForm param);
    /**
     * 通过表单查询对应课程下的学生
     *
     * @param courseId 课程ID
     * @return 学生列表
     */
    List<UserVO> queryStudentsByCourseId(Long courseId);
}

