package homework.web.util.beans;


import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @param <T> 普通数据响应
 */
public class CommonResult<T> extends ResponseEntity<Map<String, Object>> {
    private int status;
    private String message;
    private T data;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public CommonResult(final int status, final String message, final T data) {
        super(new HashMap<String, Object>(3) {
            private static final long serialVersionUID = 8252373729770515446L;

            {
                this.put("status", status);
                this.put("message", message);
                this.put("data", data);
            }
        }, HttpStatus.valueOf(status));
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResult<T> success(T data) {
        return success(data, HttpStatus.OK.getReasonPhrase());
    }

    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult(HttpStatus.OK.value(), message, data);
    }

    public static <T> CommonResult<T> error(HttpStatus status) {
        return error(status, status.getReasonPhrase());
    }

    public static <T> CommonResult<T> error(HttpStatus status, String message) {
        return new CommonResult(status.value(), message, (Object)null);
    }

    public static <T> CommonResult<T> error(HttpStatus status, String message, T data) {
        return new CommonResult(status.value(), message, data);
    }
}

