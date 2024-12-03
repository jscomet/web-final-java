package homework.web.service;


import com.baomidou.mybatisplus.extension.service.IService;
import homework.web.entity.po.QuestionBank;
import homework.web.entity.dto.QuestionBankQuery;
import homework.web.entity.vo.QuestionBankVO;

import java.util.List;

/**
 * 题库(QuestionBank)表服务接口
 *
 * @author jscomet
 * @since 2024-12-02 22:51:57
 */
public interface QuestionBankService extends IService<QuestionBank> {
    /**
     * 通过ID查询单条数据
     *
     * @param questionId 主键
     * @return 实例对象
     */
    QuestionBankVO queryById(Long questionId);

    /**
     * 查询多条数据
     *
     * @param current 查询页面
     * @param pageSize 查询条数
     * @param param 查询参数
     * @return 对象列表
     */
    List<QuestionBankVO> queryAll(int current, int pageSize, QuestionBankQuery param);

    /**
     * 通过实体作为筛选条件计数
     *
     * @param param 查询参数
     * @return 数量
     */
    int count(QuestionBankQuery param);

}

