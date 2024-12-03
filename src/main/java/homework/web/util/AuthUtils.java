package homework.web.util;

import homework.web.constant.enums.RoleType;
import homework.web.context.CurrentUserContext;
import homework.web.entity.po.Role;
import homework.web.entity.vo.UserVO;

public class AuthUtils {

    public static UserVO getUserDetails() {
        return CurrentUserContext.getCurrentUser();
    }

    public static Long getCurrentUserId() {
        return getUserDetails() !=null ?getUserDetails().getUserId():null;
    }

    public static void setUserDetails(UserVO user) {
        CurrentUserContext.setCurrentUser(user);
    }

    public static void removeUserDetails() {
        CurrentUserContext.removeCurrentUser();
    }

    /**
     * 用户是否登录
     *
     * @return 是否
     */
    public static boolean isAuthenticated() {
        return getUserDetails() != null && getUserDetails().getUserId() != null;
    }

    public static boolean hasRole(RoleType type) {
        return hasRole(type.name());
    }

    public static boolean hasRole(String authority) {
        UserVO userDetails = getUserDetails();
        return userDetails.getRoles().stream().map(Role::getEname).anyMatch(authority::equals);
    }

    public static boolean hasAnyRole(RoleType... types) {
        for (RoleType type : types) {
            if (hasRole(type)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasAnyRole(String... authorities) {
        for (String authority : authorities) {
            if (hasRole(authority)) {
                return true;
            }
        }
        return false;
    }

}
