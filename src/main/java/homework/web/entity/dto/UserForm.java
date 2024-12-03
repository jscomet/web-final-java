package homework.web.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import homework.web.config.valid.AddGroup;
import homework.web.config.valid.UpdateGroup;
import homework.web.entity.po.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serial;
import java.util.List;

/**
 * 用户表单参数
 *
 * @author 30597
 * @since 2024-12-02 23:49
 */
@Data
public class UserForm implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @NotBlank(groups = AddGroup.class, message = "用户名不能为空")
    @Null(groups = UpdateGroup.class, message = "用户名不可修改")
    private String username;
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;

    /**
     * 学号
     */
    @Schema(description = "学号")
    private String studentId;
    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;
    /**
     * 头像URL
     */
    @Schema(description = "头像URL")
    private String avatarUrl;
    /**
     * 个性签名
     */
    @Schema(description = "个性签名")
    private String signature;

    @Schema(description = "性别")
    private User.Gender gender;

    @Schema
    @Null(groups = UpdateGroup.class,message = "不能修改用户密码")
    private String password;

    @Schema(description = "管理员有效！！！用户角色id")
    private List<Integer> roleIds;
}
