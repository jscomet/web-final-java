package homework.web.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * Description
 *
 * @author 30597
 * @since 2024-12-07 19:07
 */
@Data
public class UserStudentRegisterParam implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "学号")
    @NotBlank(message = "学号不能为空")
    private String studentId;
    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
