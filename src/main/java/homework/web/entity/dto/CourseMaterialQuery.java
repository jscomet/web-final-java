package homework.web.entity.dto;

import homework.web.entity.po.CourseMaterial;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 课件资料(CourseMaterial)查询参数
 *
 * @author jscomet
 * @since 2024-12-02 21:54:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseMaterialQuery extends CourseMaterial {
}
