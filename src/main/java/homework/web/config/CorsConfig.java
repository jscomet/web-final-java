package homework.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 全局跨域配置
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        // 创建CORS配置对象
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*"); // 设置支持域
        config.setAllowCredentials(true); // 是否发送cookie
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // 支持的请求方式
        config.addAllowedHeader("*"); // 允许的原始请求头部信息
        config.addExposedHeader("*"); // 暴露的头部信息
        // 添加地址映射
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", config);
        // 返回CorsFilter对象
        return new CorsFilter(corsConfigurationSource);
    }
}
