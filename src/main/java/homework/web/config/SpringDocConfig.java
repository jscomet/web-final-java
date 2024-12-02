package homework.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 生成swagger文档，访问地址
 * <p><a href="http://localhost:8080/swagger-ui/index.html#/">swagger文档</a></p>
 */
@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI(SpecVersion.V30)
                .info(new Info().title("活动申请x").description("活动申请后台"));
    }
}