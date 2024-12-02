package homework.web.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "data.redis")
public class RedisProperty {
    private String host;
    private String port;
    private String password;
}
