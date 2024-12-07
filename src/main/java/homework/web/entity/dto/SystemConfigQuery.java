package homework.web.entity.dto;

import homework.web.entity.po.SystemConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统配置(SystemConfig)查询参数
 *
 * @author jscomet
 * @since 2024-12-07 19:27:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemConfigQuery extends SystemConfig {
}
