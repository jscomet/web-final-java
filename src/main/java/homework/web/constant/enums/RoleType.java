package homework.web.constant.enums;


import lombok.Getter;

public enum RoleType {

    /**
     * 超级管理员
     */
    SUPER_ADMIN(1, "超级管理员"),
    /**
     * 老师
     */
    TEACHER(2, "老师"),
    /**
     * 学生
     */
    STUDENT(3, "学生"),
    /**
     * 游客
     */
    GUEST(4, "游客")
    ;
    @Getter
    private final Integer value;
    @Getter
    private final String desc;
    RoleType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
    public static final RoleType[] values = values();
}
