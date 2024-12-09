package homework.web.config;

import homework.web.interceptor.JwtLoginInterceptor;
import homework.web.interceptor.PermissionAuthorizeInterceptor;
import homework.web.json.JacksonObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

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
    @Resource
    private EnumConvertFactory enumConvertFactory;

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

    /**
     * 扩展Spring MVC框架的消息转化器
     *
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器...");
        //创建一个消息转换器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        //需要为消息转换器设置一个对象转换器，对象转换器可以将Java对象序列化为json数据
        converter.setObjectMapper(new JacksonObjectMapper());
        //将自己的消息转化器加入容器中
        converters.add(0, converter);
    }

    /**
     * 注册自定义枚举类型转换器
     * @param registry
     */
    @Override
    protected void addFormatters(FormatterRegistry registry) {
        log.info("注册自定义枚举类型转换器...");
        registry.addConverterFactory(enumConvertFactory);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //是否发送Cookie
                .allowCredentials(true)
                //放行哪些原始域
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }


}
