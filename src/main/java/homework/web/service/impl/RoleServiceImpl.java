package homework.web.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.RoleDao;
import homework.web.entity.po.Role;
import homework.web.service.RoleService;
import homework.web.entity.dto.RoleQuery;
import homework.web.entity.po.Role;
import homework.web.entity.vo.RoleVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;
/**
 * 角色(Role)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 19:37:40
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {
    @Resource
    private RoleDao roleDao;

    @Override
    public RoleVO queryById(Integer roleId) {
        return roleDao.queryById(roleId);
    }

    @Override
    public List<RoleVO> queryAll(int current, int pageSize, RoleQuery param) {
        PageHelper.startPage(current, pageSize);
        return roleDao.queryAll(param);
    }

    @Override
    public int count(RoleQuery param) {
        return roleDao.count(param);
    }
}

