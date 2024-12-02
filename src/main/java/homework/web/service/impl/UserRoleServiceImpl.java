package homework.web.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.UserRoleDao;
import homework.web.entity.po.UserRole;
import homework.web.service.UserRoleService;
import homework.web.entity.dto.UserRoleQuery;
import homework.web.entity.po.UserRole;
import homework.web.entity.vo.UserRoleVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;
/**
 * (UserRole)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 19:37:18
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {
    @Resource
    private UserRoleDao userRoleDao;

    @Override
    public UserRoleVO queryById(Integer userId) {
        return userRoleDao.queryById(userId);
    }

    @Override
    public List<UserRoleVO> queryAll(int current, int pageSize, UserRoleQuery param) {
        PageHelper.startPage(current, pageSize);
        return userRoleDao.queryAll(param);
    }

    @Override
    public int count(UserRoleQuery param) {
        return userRoleDao.count(param);
    }
}

