package homework.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.web.entity.po.StudyNote;
import homework.web.entity.dto.StudyNoteQuery;
import homework.web.entity.vo.StudyNoteVO;

import java.util.List;

/**
 * 学习笔记(StudyNote)表服务接口
 *
 * @author jscomet
 * @since 2024-12-02 22:51:31
 */
public interface StudyNoteService extends IService<StudyNote> {
    /**
     * 通过ID查询单条数据
     *
     * @param noteId 主键
     * @return 实例对象
     */
    StudyNoteVO queryById(Long noteId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<StudyNoteVO> queryAll(int current, int pageSize, StudyNoteQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(StudyNoteQuery param);

}

