package homework.web.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 密码加密工具类测试
 *
 * @author 30597
 * @since 2024-12-03 0:28
 */
class PasswordUtilsTest {

    @Test
    void generateSalt() {
        //测试生成盐
        String salt = PasswordUtils.generateSalt();
        System.out.println(salt);
        assertNotNull(salt);
    }

    @Test
    void encryptPassword() {
        //测试加密密码
        String password="123456";
        String salt = PasswordUtils.generateSalt();
        String encryptPassword = PasswordUtils.encryptPassword(password, salt);
        System.out.println(encryptPassword);
        assertNotNull(encryptPassword);
    }

    @Test
    void verifyPassword() {
        //测试验证密码
        String password="123456";
        String salt = PasswordUtils.generateSalt();
        String encryptPassword = PasswordUtils.encryptPassword(password, salt);
        boolean verifyPassword = PasswordUtils.verifyPassword(password, salt, encryptPassword);
        assertTrue(verifyPassword);

    }

}