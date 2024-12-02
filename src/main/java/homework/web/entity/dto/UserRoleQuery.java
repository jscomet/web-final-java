package homework.web.entity.dto;

import homework.web.entity.po.UserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (UserRole)查询参数
 *
 * @author jscomet
 * @since 2024-12-02 19:37:18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserRoleQuery extends UserRole {
}
