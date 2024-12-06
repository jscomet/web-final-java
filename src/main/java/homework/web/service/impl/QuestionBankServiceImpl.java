package homework.web.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.QuestionBankDao;
import homework.web.entity.po.QuestionBank;
import homework.web.service.QuestionBankService;
import homework.web.entity.dto.QuestionBankQuery;
import homework.web.entity.po.QuestionBank;
import homework.web.entity.vo.QuestionBankVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;
/**
 * 题库(QuestionBank)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 22:51:57
 */
@Service("questionBankService")
public class QuestionBankServiceImpl extends ServiceImpl<QuestionBankDao, QuestionBank> implements QuestionBankService {
    @Resource
    private QuestionBankDao questionBankDao;

    @Override
    public QuestionBankVO queryById(Long questionId) {
        return questionBankDao.queryById(questionId);
    }

    @Override
    public List<QuestionBankVO> queryAll(int current, int pageSize, QuestionBankQuery param) {
        PageHelper.startPage(current, pageSize);
        return questionBankDao.queryAll(param);
    }

    @Override
    public int count(QuestionBankQuery param) {
        return questionBankDao.count(param);
    }
}

