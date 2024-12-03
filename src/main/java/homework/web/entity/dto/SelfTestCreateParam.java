package homework.web.entity.dto;

import homework.web.entity.po.SelfTest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Description
 *
 * @author 30597
 * @since 2024-12-03 13:39
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SelfTestCreateParam extends SelfTest {
    /**
     * 题目ID列表
     */
    @Schema(description = "题目ID列表，题目顺序为列表顺序")
    private List<Long> questionIds;
}
