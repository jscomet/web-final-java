package homework.web;

import homework.web.entity.po.User;
import homework.web.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = WebApplication.class)
class WebApplicationTests {
    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
    }

}
