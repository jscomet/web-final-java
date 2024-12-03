package homework.web.config.valid;

import homework.web.util.beans.Sorter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Sorter 校验器
 *
 * @author L.K.
 * @since 2023-03-11
 */
public class SorterValidator implements ConstraintValidator<SorterValidated, Sorter> {
    @Override
    public boolean isValid(Sorter sorter, ConstraintValidatorContext constraintValidatorContext) {
        return Sorter.check(sorter);
    }
}
