package homework.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.constant.enums.RoleType;
import homework.web.dao.UserDao;
import homework.web.entity.dto.*;
import homework.web.entity.po.CourseEnrollment;
import homework.web.entity.po.User;
import homework.web.entity.po.UserRole;
import homework.web.service.*;
import homework.web.entity.vo.UserVO;
import homework.web.util.AssertUtils;
import homework.web.util.AuthUtils;
import homework.web.util.JwtUtils;
import homework.web.util.PasswordUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户(User)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 20:23:54
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Resource
    private UserDao userDao;
    @Resource
    private RoleService roleService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private CourseEnrollmentService courseEnrollmentService;

    @Override
    public UserVO queryById(Long userId) {
        UserVO user = userDao.queryById(userId);
        fillVO(user);
        return user;
    }

    @Override
    public List<UserVO> queryAll(int current, int pageSize, UserQuery param) {
        PageHelper.startPage(current, pageSize);
        List<UserVO> list = userDao.queryAll(param);
        list.forEach(this::fillVO);
        return list;
    }

    public void fillVO(UserVO user) {
        if (user != null && user.getUserId() != null) {

            user.setRoles(roleService.queryByUserId(user.getUserId()));
        }
    }

    @Override
    public int count(UserQuery param) {
        return userDao.count(param);
    }

    @Override
    public boolean resetPassword(Long userId, String newPassword) {
        //生成新的密码和盐
        PasswordUtils.PasswordAndSalt passwordAndSalt = PasswordUtils.createPassword(newPassword);
        //更新密码和盐
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getUserId, userId)
                .set(User::getPassword, passwordAndSalt.getPassword())
                .set(User::getSalt, passwordAndSalt.getSalt());
        return this.update(updateWrapper);
    }

    @Override
    public boolean enableDisableUser(Long id) {
        //判断用户是否存在
        User user = this.getById(id);
        AssertUtils.notNull(user, HttpStatus.NOT_FOUND, "用户不存在");
        //判断用户是否启用
        User.Status newStatus = user.getStatus() == User.Status.ENABLED ? User.Status.DISABLED : User.Status.ENABLED;
        //更新用户状态
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getUserId, id)
                .set(User::getStatus, newStatus);
        return this.update(updateWrapper);
    }

    @Override
    public String loginByPassword(UserLoginPasswordParam param) {
        //查询用户
        User user = this.lambdaQuery().eq(User::getUsername, param.getUsername()).one();
        AssertUtils.notNull(user, HttpStatus.NOT_FOUND, "用户不存在");
        //验证密码
        AssertUtils.isTrue(PasswordUtils.verifyPassword(param.getPassword(), user.getPassword(), user.getSalt()), HttpStatus.BAD_REQUEST, "密码错误");
        //返回token
        return JwtUtils.createJWTByUserId(user.getUserId());
    }

    @Override
    public void logout() {

    }

    @Override
    public boolean updatePassword(UserPasswordParam param) {
        //查询用户
        User user = this.lambdaQuery().eq(User::getUserId, param.getUserId()).one();
        AssertUtils.notNull(user, HttpStatus.NOT_FOUND, "用户不存在");
        //验证密码
        AssertUtils.isTrue(PasswordUtils.verifyPassword(param.getOldPassword(), user.getPassword(), user.getSalt()), HttpStatus.BAD_REQUEST, "原密码错误");
        //生成新的密码和盐
        PasswordUtils.PasswordAndSalt passwordAndSalt = PasswordUtils.createPassword(param.getNewPassword());
        //更新密码和盐
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getUserId, param.getUserId())
                .set(User::getPassword, passwordAndSalt.getPassword())
                .set(User::getSalt, passwordAndSalt.getSalt());
        return this.update(updateWrapper);
    }


    @Override
    public void desensitize(UserVO user) {
        if (user == null) {
            return;
        }
        user.setPassword(null);
        user.setSalt(null);
    }

    private void insertOrUpdateRoles(Long userId, List<Integer> roleIds) {
        //1.删除用户角色信息
        userRoleService.lambdaUpdate().eq(UserRole::getUserId, userId).remove();
        List<UserRole> userRoles = roleIds.stream().map(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            return userRole;
        }).toList();
        //2.新增用户角色信息
        userRoleService.saveBatch(userRoles);
    }

    @Override
    @Transactional
    public boolean updateByForm(Long id, UserForm param) {
        //查询用户
        User user = this.getById(id);
        AssertUtils.notNull(user, HttpStatus.NOT_FOUND, "用户不存在");
        //更新用户信息
        User updateUser = new User();
        BeanUtil.copyProperties(param, updateUser);
        updateUser.setUserId(id);

        this.updateById(updateUser);

        //判断是否有权限更新用户角色信息
        if (AuthUtils.hasAnyRole(RoleType.SUPER_ADMIN, RoleType.TEACHER)) {
            //更新用户角色信息
            if (CollectionUtils.isNotEmpty(param.getRoleIds())) {
                this.insertOrUpdateRoles(id, param.getRoleIds());

            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean saveByForm(UserForm param) {
        User user = new User();
        BeanUtil.copyProperties(param, user);
        //使用默认用户名作为默认密码
        AssertUtils.notNull(user.getUsername(), HttpStatus.BAD_REQUEST, "用户名不能为空");
        PasswordUtils.PasswordAndSalt passwordAndSalt = PasswordUtils.createPassword(user.getUsername());
        user.setPassword(passwordAndSalt.getPassword());
        user.setSalt(passwordAndSalt.getSalt());
        this.save(user);
        Long userId = user.getUserId();
        //判断是否有权限更新用户角色信息
        if (AuthUtils.hasAnyRole(RoleType.SUPER_ADMIN, RoleType.TEACHER)) {
            //更新用户角色信息
            if (CollectionUtils.isNotEmpty(param.getRoleIds())) {
                this.insertOrUpdateRoles(userId, param.getRoleIds());

            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String register(UserForm param) {
        User user = new User();
        BeanUtil.copyProperties(param, user);
        PasswordUtils.PasswordAndSalt passwordAndSalt = PasswordUtils.createPassword(param.getPassword());
        user.setPassword(passwordAndSalt.getPassword());
        user.setSalt(passwordAndSalt.getSalt());
        this.save(user);
        Long userId = user.getUserId();
        //更新用户角色信息
        //判断是否有权限更新用户角色信息
        if (AuthUtils.hasAnyRole(RoleType.SUPER_ADMIN, RoleType.TEACHER)) {
            //更新用户角色信息
            if (CollectionUtils.isNotEmpty(param.getRoleIds())) {
                this.insertOrUpdateRoles(userId, param.getRoleIds());

            }
        }
        // 根据userId生成token
        return JwtUtils.createJWTByUserId(userId);
    }

    @Override
    public List<UserVO> queryStudentsByCourseId(Long courseId) {

        List<Long> studentIds = courseEnrollmentService.lambdaQuery().eq(CourseEnrollment::getCourseId, courseId)
                .ne(CourseEnrollment::getStatus, CourseEnrollment.Status.QUIT)
                .list()
                .stream()
                .map(CourseEnrollment::getStudentId)
                .toList();
        if (CollectionUtils.isEmpty(studentIds)) {
            return List.of();
        }
        List<UserVO> students = this.lambdaQuery().in(User::getUserId, studentIds).list().stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtil.copyProperties(user, userVO);
            return userVO;
        }).toList();
        students.forEach(this::fillVO);
        return students;
    }
}

