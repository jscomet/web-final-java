package homework.web.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.StudyNoteDao;
import homework.web.entity.po.StudyNote;
import homework.web.service.StudyNoteService;
import homework.web.entity.dto.StudyNoteQuery;
import homework.web.entity.po.StudyNote;
import homework.web.entity.vo.StudyNoteVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;
/**
 * 学习笔记(StudyNote)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 22:51:31
 */
@Service("studyNoteService")
public class StudyNoteServiceImpl extends ServiceImpl<StudyNoteDao, StudyNote> implements StudyNoteService {
    @Resource
    private StudyNoteDao studyNoteDao;

    @Override
    public StudyNoteVO queryById(Long noteId) {
        return studyNoteDao.queryById(noteId);
    }

    @Override
    public List<StudyNoteVO> queryAll(int current, int pageSize, StudyNoteQuery param) {
        PageHelper.startPage(current, pageSize);
        return studyNoteDao.queryAll(param);
    }

    @Override
    public int count(StudyNoteQuery param) {
        return studyNoteDao.count(param);
    }
}

