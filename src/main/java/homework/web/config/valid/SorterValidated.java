package homework.web.config.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sorter 校验注解
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Constraint(validatedBy = SorterValidator.class)
public @interface SorterValidated {
    String message() default "排序不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

