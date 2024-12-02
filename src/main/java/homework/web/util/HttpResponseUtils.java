package homework.web.util;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author ousing9
 */
public class HttpResponseUtils {
    public static void respond(HttpServletResponse response, HttpStatus httpStatus) throws IOException {
        respond(response, httpStatus, null);
    }

    public static void respond(HttpServletResponse response, HttpStatus httpStatus, Object object) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        response.setStatus(httpStatus.value());response.getOutputStream().write(JSON.toJSONString(object).getBytes(StandardCharsets.UTF_8));
    }
}
