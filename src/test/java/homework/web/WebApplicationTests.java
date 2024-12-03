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
        User user = new User();
        user.setUsername("test");
        user.setPassword("123456");
        user.setSalt("111");
        userService.save(user);
        System.out.println("保存成功");
    }

}
