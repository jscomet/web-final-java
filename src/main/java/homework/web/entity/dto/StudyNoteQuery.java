package homework.web.entity.dto;

import homework.web.entity.po.StudyNote;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 学习笔记(StudyNote)查询参数
 *
 * @author jscomet
 * @since 2024-12-02 22:51:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudyNoteQuery extends StudyNote {
}
