package homework.web.util;

import homework.web.constant.JwtClaimsConstant;
import homework.web.constant.enums.RoleType;
import homework.web.context.CurrentUserContext;
import homework.web.entity.po.Role;
import homework.web.entity.vo.UserVO;
import homework.web.property.AppProperty;
import homework.web.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

public class AuthUtils {
    @Resource
    private UserService userService;
    private static UserService service;

    @Resource(name = "appProperty")
    private AppProperty _appProperty;
    private static AppProperty appProperty;

    @PostConstruct
    public void init() {
        AuthUtils.service = this.userService;
        AuthUtils.appProperty = this._appProperty;
    }


    public static String createJwtToken(String userId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put(JwtClaimsConstant.USER_ID, userId);
        return JwtUtils.createJWT(appProperty.getJwt().getSecretKey(), appProperty.getJwt().getTtl(), payload);
    }

    public static UserVO getUserDetails() {
        return CurrentUserContext.getCurrentUser();
    }

    public static void setUserDetails(UserVO user) {
        CurrentUserContext.setCurrentUser(user);
    }

    public static void removeUserDetails() {
        CurrentUserContext.removeCurrentUser();
    }

    /**
     * 是否有权限访问
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
