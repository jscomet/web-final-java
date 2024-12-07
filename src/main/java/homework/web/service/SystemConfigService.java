package homework.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.web.entity.po.SystemConfig;
import homework.web.entity.dto.SystemConfigQuery;
import homework.web.entity.vo.SystemConfigVO;

import java.util.List;

/**
 * 系统配置(SystemConfig)表服务接口
 *
 * @author jscomet
 * @since 2024-12-07 19:27:25
 */
public interface SystemConfigService extends IService<SystemConfig> {

    /**
     * 是否启用自主注册功能
     */
    boolean isEnableSelfRegister();

    /**
     * 设置是否启用自主注册功能
     */
    boolean setEnableSelfRegister(boolean enable);
}

