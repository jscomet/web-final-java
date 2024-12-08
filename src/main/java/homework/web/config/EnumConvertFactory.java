package homework.web.config;

import com.baomidou.mybatisplus.annotation.IEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Description
 *
 * @author 30597
 * @since 2024-12-08 21:07
 */

@Component
public class EnumConvertFactory implements ConverterFactory<String, IEnum<?>> {

    @Override
    public <T extends IEnum<?>> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnum<>(targetType);
    }


    public static class StringToEnum<T extends IEnum<?>> implements Converter<String, T> {

        private final Class<T> targetType;

        public StringToEnum(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(String source) {
            if (!StringUtils.hasText(source)) {
                return null;
            }
            return (T) EnumConvertFactory.getEnum(this.targetType, source);
        }
    }

    public static <T extends IEnum<?>> T getEnum(Class<T> targetType, String source) {
        for (T constant : targetType.getEnumConstants()) {
            if (source.equals(String.valueOf(constant.getValue()))) {
                return constant;
            }
        }
        return null;
    }
}
