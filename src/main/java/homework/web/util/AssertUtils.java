package homework.web.util;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

import homework.web.exception.HttpErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

/**
 * 假言鉴权类
 */
public abstract class AssertUtils {
    public AssertUtils() {
    }

    public static void isNull(Object o, HttpStatus status) {
        if (o != null) {
            throw new HttpErrorException(status);
        }
    }

    public static void isNull(Object o, HttpStatus status, String message) {
        if (o != null) {
            throw new HttpErrorException(status, message);
        }
    }

    public static void notNull(Object o, HttpStatus status) {
        if (o == null) {
            throw new HttpErrorException(status);
        }
    }

    public static void notNull(Object o, HttpStatus status, String message) {
        if (o == null) {
            throw new HttpErrorException(status, message);
        }
    }

    public static void isTrue(boolean expression, HttpStatus status) {
        if (!expression) {
            throw new HttpErrorException(status);
        }
    }

    public static void isTrue(boolean expression, HttpStatus status, String message) {
        if (!expression) {
            throw new HttpErrorException(status, message);
        }
    }

    public static void isTrue(Supplier<Boolean> supplier, HttpStatus status, String message) {
        boolean b;
        try {
            b = (Boolean)supplier.get();
        } catch (Exception var5) {
            b = false;
        }

        if (!b) {
            throw new HttpErrorException(status, message);
        }
    }

    public static void isFalse(boolean expression, HttpStatus status) {
        if (expression) {
            throw new HttpErrorException(status);
        }
    }

    public static void isFalse(boolean expression, HttpStatus status, String message) {
        if (expression) {
            throw new HttpErrorException(status, message);
        }
    }

    public static void isFalse(Supplier<Boolean> supplier, HttpStatus status, String message) {
        boolean b;
        try {
            b = (Boolean)supplier.get();
        } catch (Exception var5) {
            b = true;
        }

        if (b) {
            throw new HttpErrorException(status, message);
        }
    }

    public static void isBlank(String str, HttpStatus status) {
        if (StringUtils.isNotBlank(str)) {
            throw new HttpErrorException(status);
        }
    }

    public static void isBlank(String str, HttpStatus status, String message) {
        if (StringUtils.isNotBlank(str)) {
            throw new HttpErrorException(status, message);
        }
    }

    public static void notBlank(String str, HttpStatus status) {
        if (StringUtils.isBlank(str)) {
            throw new HttpErrorException(status);
        }
    }

    public static void notBlank(String str, HttpStatus status, String message) {
        if (StringUtils.isBlank(str)) {
            throw new HttpErrorException(status, message);
        }
    }

    public static void isEmpty(String str, HttpStatus status) {
        if (!StringUtils.isEmpty(str)) {
            throw new HttpErrorException(status);
        }
    }

    public static void isEmpty(String str, HttpStatus status, String message) {
        if (!StringUtils.isEmpty(str)) {
            throw new HttpErrorException(status, message);
        }
    }

    public static void isEmpty(Collection<?> collection, HttpStatus status) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new HttpErrorException(status);
        }
    }

    public static void isEmpty(Collection<?> collection, HttpStatus status, String message) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new HttpErrorException(status, message);
        }
    }

    public static void isEmpty(Map<?, ?> map, HttpStatus status) {
        if (!CollectionUtils.isEmpty(map)) {
            throw new HttpErrorException(status);
        }
    }

    public static void isEmpty(Map<?, ?> map, HttpStatus status, String message) {
        if (!CollectionUtils.isEmpty(map)) {
            throw new HttpErrorException(status, message);
        }
    }

    public static void notEmpty(String str, HttpStatus status) {
        if (StringUtils.isEmpty(str)) {
            throw new HttpErrorException(status);
        }
    }

    public static void notEmpty(String str, HttpStatus status, String message) {
        if (StringUtils.isEmpty(str)) {
            throw new HttpErrorException(status, message);
        }
    }

    public static void notEmpty(Collection<?> collection, HttpStatus status) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new HttpErrorException(status);
        }
    }

    public static void notEmpty(Collection<?> collection, HttpStatus status, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new HttpErrorException(status, message);
        }
    }

    public static void notEmpty(Map<?, ?> map, HttpStatus status) {
        if (CollectionUtils.isEmpty(map)) {
            throw new HttpErrorException(status);
        }
    }

    public static void notEmpty(Map<?, ?> map, HttpStatus status, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new HttpErrorException(status, message);
        }
    }
}

