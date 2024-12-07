package homework.web.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;

/**
 * 老师注册参数
 *
 * @author 30597
 * @since 2024-12-07 19:20
 */
@Data
public class UserTeacherRegisterParam implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;
    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
