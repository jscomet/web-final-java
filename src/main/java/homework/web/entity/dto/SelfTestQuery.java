package homework.web.entity.dto;

import homework.web.entity.po.SelfTest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自测试卷(SelfTest)查询参数
 *
 * @author jscomet
 * @since 2024-12-02 22:51:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SelfTestQuery extends SelfTest {
}
