package homework.web.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import homework.web.dao.CourseMaterialDao;
import homework.web.entity.po.CourseMaterial;
import homework.web.service.CourseMaterialService;
import homework.web.entity.dto.CourseMaterialQuery;
import homework.web.entity.po.CourseMaterial;
import homework.web.entity.vo.CourseMaterialVO;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;

import java.util.List;
/**
 * 课件资料(CourseMaterial)表服务实现类
 *
 * @author jscomet
 * @since 2024-12-02 21:54:22
 */
@Service("courseMaterialService")
public class CourseMaterialServiceImpl extends ServiceImpl<CourseMaterialDao, CourseMaterial> implements CourseMaterialService {
    @Resource
    private CourseMaterialDao courseMaterialDao;

    @Override
    public CourseMaterialVO queryById(Long materialId) {
        return courseMaterialDao.queryById(materialId);
    }

    @Override
    public List<CourseMaterialVO> queryAll(int current, int pageSize, CourseMaterialQuery param) {
        PageHelper.startPage(current, pageSize);
        return courseMaterialDao.queryAll(param);
    }

    @Override
    public int count(CourseMaterialQuery param) {
        return courseMaterialDao.count(param);
    }
}

