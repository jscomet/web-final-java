package homework.web.annotation;


import homework.web.constant.enums.RoleType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionAuthorize {
    /**
     * 设置默认允许通过的角色类型
     */
    RoleType[] value() default {RoleType.STUDENT, RoleType.TEACHER, RoleType.SUPER_ADMIN};


}
