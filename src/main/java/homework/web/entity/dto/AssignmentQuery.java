package homework.web.entity.dto;

import homework.web.entity.po.Assignment;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 作业(Assignment)查询参数
 *
 * @author jscomet
 * @since 2024-12-02 21:53:13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssignmentQuery extends Assignment {
}
