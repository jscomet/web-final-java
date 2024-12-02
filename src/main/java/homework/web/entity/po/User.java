package homework.web.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户(User)实体类
 *
 * @author jscomet
 * @since 2024-12-02 19:36:59
 */
@Data
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = -19781250009255919L;
    /**
     * 用户ID
     */
    @Schema(description = "用户ID")    
    @TableId(value = "user_id")
    private Long userId;


    /**
     * 用户名
     */
    @Schema(description = "用户名")    
    @TableField(value = "username")
    private String username;

    /**
     * 密码哈希值
     */
    @Schema(description = "密码哈希值")    
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
     * 角色id
     */
    @Schema(description = "角色id")    
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
     * 个性签名
     */
    @Schema(description = "个性签名")    
    @TableField(value = "signature")
    private String signature;

    /**
     * 账号状态：0-活跃、1-禁用、2-已删除
     */
    @Schema(description = "账号状态：0-活跃、1-禁用、2-已删除")    
    @TableField(value = "status")
    private Integer status;

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
