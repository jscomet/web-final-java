package homework.web.entity.dto;

import homework.web.entity.po.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色(Role)查询参数
 *
 * @author jscomet
 * @since 2024-12-02 20:26:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleQuery extends Role {
}
