package homework.web.exception;

import homework.web.util.beans.CommonResult;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 自定义 HTTP 状态码异常
     */
    @ExceptionHandler(HttpErrorException.class)
    @ResponseBody
    public CommonResult<Object> handleException(HttpErrorException e) {
        if (e.getMessage() != null) {
            return CommonResult.error(e.getStatus(), e.getMessage());
        } else {
            return CommonResult.error(e.getStatus());
        }
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public CommonResult<Object> handleException(ConstraintViolationException e) {
        if (e.getMessage() != null) {
            int index = e.getMessage().indexOf(':') + 2;
            return CommonResult.error(BAD_REQUEST, e.getMessage().substring(index));
        } else {
            return CommonResult.error(BAD_REQUEST);
        }
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonResult<Object> handleException(MethodArgumentNotValidException e) {
        if (e != null) {
            BindingResult result = e.getBindingResult();
            StringBuilder builder = new StringBuilder();
            for (FieldError fieldError : result.getFieldErrors()) {
                builder.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(";");
            }
            return CommonResult.error(BAD_REQUEST, builder.toString());
        } else {
            return CommonResult.error(BAD_REQUEST);
        }
    }

    /**
     * 非法参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public CommonResult<Object> handleException(IllegalArgumentException e) {
        if (e.getMessage() != null) {
            return CommonResult.error(BAD_REQUEST, e.getMessage());
        } else {
            return CommonResult.error(BAD_REQUEST);
        }
    }

    /**
     * 未实现异常
     */
    @ExceptionHandler(NotImplementedException.class)
    @ResponseBody
    public CommonResult<Object> handleException(NotImplementedException e) {
        if (e.getMessage() != null) {
            return CommonResult.error(INTERNAL_SERVER_ERROR, e.getMessage());
        } else {
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 文件超过大小异常
     */
    @ExceptionHandler(SizeLimitExceededException.class)
    @ResponseBody
    public CommonResult<Object> exceptionHandler(SizeLimitExceededException ex) {
        return CommonResult.error(HttpStatus.BAD_REQUEST, "文件上传大小%.2fMB超过限制大小%.2fMB".formatted(1.0 * ex.getActualSize() / 1024 / 1024, 1.0 * ex.getPermittedSize() / 1024 / 1024));
    }
}
