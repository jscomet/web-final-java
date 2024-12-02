package homework.web.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.UserDao;
import homework.web.entity.po.User;
import homework.web.service.UserService;
import homework.web.entity.dto.UserQuery;
import homework.web.entity.po.User;
import homework.web.entity.vo.UserVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;
/**
 * 用户(User)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 19:37:00
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Resource
    private UserDao userDao;

    @Override
    public UserVO queryById(Long userId) {
        return userDao.queryById(userId);
    }

    @Override
    public List<UserVO> queryAll(int current, int pageSize, UserQuery param) {
        PageHelper.startPage(current, pageSize);
        return userDao.queryAll(param);
    }

    @Override
    public int count(UserQuery param) {
        return userDao.count(param);
    }
}

