package homework.web.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Data
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperty {
    private String domain;
    private final Front front = new Front();
    private final Back back = new Back();
    private final JwtProperty jwt = new JwtProperty();
    private final WeChatProperty applet = new WeChatProperty();

    @Data
    public static class Front {
        private String url;
        private String indexUrl;
        private String errorUrl;
    }

    @Data
    public static class Back {
        private String url;
        private String loginUrl;
        private String logoutUrl;
        private String uploadPath;
    }
    @Data
    public static class JwtProperty {
        private String secretKey;
        private long ttl;
        private String tokenName;
    }

    /**
     * 微信配置
     */
    @Data
    public static class WeChatProperty {
        private String appid;
        private String secret;
    }


}
