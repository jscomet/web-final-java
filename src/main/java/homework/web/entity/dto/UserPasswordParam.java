package homework.web.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Description
 *
 * @author 30597
 * @since 2024-12-03 1:10
 */
@Data
public class UserPasswordParam implements java.io.Serializable {
    @Schema(description = "用户ID")
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    @NotNull(message = "用户ID不能为空")
    private Long userId;
    /**
     * 旧密码
     */
    @Schema(description = "旧密码")
    @NotNull(message = "旧密码不能为空")
    private String oldPassword;
    /**
     * 新密码
     */
    @Schema(description = "新密码")
    @NotNull(message = "新密码不能为空")
    private String newPassword;
}
