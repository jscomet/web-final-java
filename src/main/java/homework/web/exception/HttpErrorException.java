package homework.web.exception;

import org.springframework.http.HttpStatus;

/**
 * 响应服务器异常
 */
public class HttpErrorException extends RuntimeException {
    private HttpStatus status;
    private Object data;

    public HttpErrorException(HttpStatus status) {
        this.status = status;
    }

    public HttpErrorException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpErrorException(HttpStatus status, String message, Object data) {
        super(message);
        this.status = status;
        this.data = data;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
