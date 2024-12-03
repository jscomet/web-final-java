package homework.web.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;

/**
 * 用户登录认证VO
 *
 * @author 30597
 * @since 2024-12-03 0:52
 */
@Data
public class UserAuthVO implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private String token;
}
