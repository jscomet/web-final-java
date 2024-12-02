package homework.web.context;


import homework.web.entity.vo.UserVO;

public class CurrentUserContext {
    public static ThreadLocal<UserVO> currentUser = new ThreadLocal<>();

    public static UserVO getCurrentUser() {
        return currentUser.get();
    }

    public static void setCurrentUser(UserVO user) {
        currentUser.set(user);
    }

    public static void removeCurrentUser() {
        currentUser.remove();
    }
}
