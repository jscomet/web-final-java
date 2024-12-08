package homework.web.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;

/**
 * 用户登录参数
 *
 * @author 30597
 * @since 2024-12-03 0:50
 */
@Data
public class UserLoginPasswordParam implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 学号/教职工号
     */
    @Schema(description = "学号/教职工号")
    @NotBlank(message = "学号/教职工号不能为空")
    private String studentId;
    /**
     * 密码
     */
    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
