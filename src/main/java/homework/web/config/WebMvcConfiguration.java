package homework.web.config;

import homework.web.interceptor.JwtLoginInterceptor;
import homework.web.interceptor.PermissionAuthorizeInterceptor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    @Resource
    private JwtLoginInterceptor jwtLoginInterceptor;
    @Resource
    private PermissionAuthorizeInterceptor permissionAuthorizeInterceptor;

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        //先根据jwt获取当前用户身份，再对接口的权限进行校验
        registry.addInterceptor(jwtLoginInterceptor)
                .addPathPatterns("/**");
        registry.addInterceptor(permissionAuthorizeInterceptor)
                .addPathPatterns("/**");
    }

    /**
     * 设置静态资源映射，主要是访问接口文档（html、js、css）
     *
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始设置静态资源映射...");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


}
