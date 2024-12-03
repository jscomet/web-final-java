package homework.web.entity.po;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户(User)实体类
 *
 * @author jscomet
 * @since 2024-12-03 01:33:52
 */
@Data
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = -73091996989707708L;

    @Getter
    public enum Status {
        /**
         * 账号状态：0-启用、1-禁用
         */
        ENABLED(0),
        DISABLED(1);
        @EnumValue
        @JsonValue
        private final Integer value;

        Status(int value) {
            this.value = value;
        }
    }

    @Getter
    public enum Gender {
        /**
         * 性别 0 未知 1 男  2 女
         */
        UNKNOWN(0, "未知"),
        MAN(1, "男"),
        FEMALE(2, "女");
        @EnumValue
        @JsonValue
        private final Integer value;
        private final String desc;

        Gender(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public static Gender[] values = values();

        public Gender getByDesc(String desc) {
            for (Gender gender : values) {
                if (gender.desc.equals(desc)) {
                    return gender;
                }
            }

            return null;
        }

        public Gender getByValue(Integer value) {
            for (Gender gender : values) {
                if (gender.value.equals(value)) {
                    return gender;
                }
            }
            return null;
        }

    }

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;


    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    @TableField(value = "password")
    private String password;

    /**
     * 密码盐值
     */
    @Schema(description = "密码盐值")
    @TableField(value = "salt")
    private String salt;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @TableField(value = "email")
    private String email;

    /**
     * 角色ID
     */
    @Schema(description = "角色ID")
    @TableField(value = "role_id")
    private Integer roleId;

    /**
     * 学号
     */
    @Schema(description = "学号")
    @TableField(value = "student_id")
    private String studentId;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 头像URL
     */
    @Schema(description = "头像URL")
    @TableField(value = "avatar_url")
    private String avatarUrl;

    /**
     * 性别 0 未知 1 男  2 女
     */
    @Schema(description = "性别 0 未知 1 男  2 女")
    @TableField(value = "gender")
    private Gender gender;

    /**
     * 个性签名
     */
    @Schema(description = "个性签名")
    @TableField(value = "signature")
    private String signature;

    /**
     * 账号状态：0-活跃、1-禁用、2-已删除
     */
    @Schema(description = "账号状态：0-启用、1-禁用")
    @TableField(value = "status")
    private Status status;


    /**
     * 登录尝试次数
     */
    @Schema(description = "登录尝试次数")
    @TableField(value = "login_attempts")
    private Integer loginAttempts;

    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    @TableField(value = "last_login_time")
    private LocalDateTime lastLoginTime;

    /**
     * 最后登录IP
     */
    @Schema(description = "最后登录IP")
    @TableField(value = "last_login_ip")
    private String lastLoginIp;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @TableField(value = "update_time")
    private LocalDateTime updateTime;


}
