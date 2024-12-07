package homework.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.SystemConfigDao;
import homework.web.entity.po.SystemConfig;
import homework.web.service.SystemConfigService;
import homework.web.entity.dto.SystemConfigQuery;
import homework.web.entity.po.SystemConfig;
import homework.web.entity.vo.SystemConfigVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;

/**
 * 系统配置(SystemConfig)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-07 19:27:25
 */
@Service("systemConfigService")
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigDao, SystemConfig> implements SystemConfigService {
    @Resource
    private SystemConfigDao systemConfigDao;

    @Override
    public boolean isEnableSelfRegister() {
        SystemConfig systemConfig = this.lambdaQuery().eq(SystemConfig::getName, SystemConfig.Name.ENABLE_SELF_REGISTER.name()).one();
        return systemConfig != null && Boolean.parseBoolean(systemConfig.getValue());
    }

    @Override
    public boolean setEnableSelfRegister(boolean enable) {
        SystemConfig systemConfig = new SystemConfig();
        systemConfig.setName(SystemConfig.Name.ENABLE_SELF_REGISTER.name());
        systemConfig.setType(SystemConfig.Type.BOOLEAN.name());
        systemConfig.setValue(String.valueOf(enable));
        return this.saveOrUpdate(systemConfig);
    }

}

