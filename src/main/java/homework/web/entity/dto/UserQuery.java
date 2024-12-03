package homework.web.entity.dto;

import homework.web.entity.po.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户(User)查询参数
 *
 * @author jscomet
 * @since 2024-12-02 20:23:54
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQuery extends User {
}
