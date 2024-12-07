package homework.web.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.StudyNoteDao;
import homework.web.entity.po.StudyNote;
import homework.web.service.CourseService;
import homework.web.service.StudyNoteService;
import homework.web.entity.dto.StudyNoteQuery;
import homework.web.entity.po.StudyNote;
import homework.web.entity.vo.StudyNoteVO;
import homework.web.service.UserService;
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
    @Resource
    private CourseService courseService;
    @Resource
    private UserService userService;
    @Override
    public StudyNoteVO queryById(Long noteId) {
        StudyNoteVO vo = studyNoteDao.queryById(noteId);
        fillVO(vo);
        return vo;
    }

    @Override
    public List<StudyNoteVO> queryAll(int current, int pageSize, StudyNoteQuery param) {
        if(current > 0 && pageSize > 0) {
            PageHelper.startPage(current, pageSize);
        }
        List<StudyNoteVO> list = studyNoteDao.queryAll(param);
        list.forEach(this::fillVO);
        return list;

    }

    private void fillVO(StudyNoteVO vo){
        if(vo == null){
            return;
        }
        if(vo.getCourseId() != null){
            vo.setCourse(courseService.queryById(vo.getCourseId()));
        }
        if(vo.getStudentId()!=null){
            vo.setStudent(userService.queryById(vo.getStudentId()));
            userService.desensitize(vo.getStudent());
        }
    }

    @Override
    public int count(StudyNoteQuery param) {
        return studyNoteDao.count(param);
    }
}

