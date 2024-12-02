package homework.web.entity.vo;

import homework.web.entity.po.Role;
import homework.web.entity.po.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户(User)视图模型
 *
 * @author jscomet
 * @since 2024-12-02 19:37:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends User {
    List<Role> roles;
}
