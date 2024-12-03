package homework.web.util;

import org.apache.commons.codec.binary.Hex;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 密码加密工具类
 */
public class PasswordUtils {
    private static final int SALT_LENGTH = 16; // 盐值长度
    private static final String HASH_ALGORITHM = "SHA-256"; // 使用SHA-256加密算法

    /**
     * 生成随机盐值
     *
     * @return 返回16位的随机盐值
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Hex.encodeHexString(salt);
    }

    /**
     * 使用盐值对密码进行加密
     *
     * @param password 原始密码
     * @param salt     盐值
     * @return 加密后的密码
     */
    public static String encryptPassword(String password, String salt) {
        if (!StringUtils.hasText(password) || !StringUtils.hasText(salt)) {
            throw new IllegalArgumentException("Password and salt cannot be empty");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            // 将盐值和密码组合
            String saltedPassword = salt + password;
            // 进行hash计算
            byte[] hash = digest.digest(saltedPassword.getBytes());
            // 将字节数组转换为16进制字符串
            return Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hash algorithm not found", e);
        }
    }

    /**
     * 验证密码是否正确
     *
     * @param password          用户输入的密码
     * @param salt             数据库中存储的盐值
     * @param encryptedPassword 数据库中存储的加密密码
     * @return 密码是否匹配
     */
    public static boolean verifyPassword(String password, String salt, String encryptedPassword) {
        if (!StringUtils.hasText(password) || !StringUtils.hasText(salt) || !StringUtils.hasText(encryptedPassword)) {
            return false;
        }

        String newEncryptedPassword = encryptPassword(password, salt);
        return newEncryptedPassword.equals(encryptedPassword);
    }

    /**
     * 生成新的加密密码和盐值
     *
     * @param password 原始密码
     * @return 包含加密密码和盐值的对象
     */
    public static PasswordAndSalt createPassword(String password) {
        String salt = generateSalt();
        String encryptedPassword = encryptPassword(password, salt);
        return new PasswordAndSalt(encryptedPassword, salt);
    }

    /**
     * 密码和盐值的包装类
     */
    public static class PasswordAndSalt {
        private final String password;
        private final String salt;

        public PasswordAndSalt(String password, String salt) {
            this.password = password;
            this.salt = salt;
        }

        public String getPassword() {
            return password;
        }

        public String getSalt() {
            return salt;
        }
    }
} 