package homework.web.entity.dto;

import homework.web.entity.po.TestRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 考试记录(TestRecord)查询参数
 *
 * @author jscomet
 * @since 2024-12-02 22:51:14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TestRecordQuery extends TestRecord {
}
