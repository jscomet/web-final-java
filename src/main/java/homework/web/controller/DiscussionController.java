package homework.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import homework.web.annotation.PermissionAuthorize;
import homework.web.entity.dto.DiscussionQuery;
import homework.web.entity.po.Course;
import homework.web.entity.po.Discussion;
import homework.web.entity.vo.DiscussionVO;
import homework.web.service.CourseService;
import homework.web.service.DiscussionService;
import homework.web.util.AssertUtils;
import homework.web.util.AuthUtils;
import homework.web.util.beans.CommonResult;
import homework.web.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 讨论区(Discussion)表控制层
 *
 * @author jscomet
 * @since 2024-12-02 21:54:31
 */
@Tag(name = "Discussion", description = "讨论区")
@RestController
@RequestMapping("/discussion")
public class DiscussionController {
    @Resource
    private DiscussionService discussionService;
    @Resource
    private CourseService courseService;

    @Operation(summary = "获取指定讨论信息")
    @GetMapping("/info/{id}")
    public CommonResult<DiscussionVO> getDiscussion(@PathVariable Long id) {
        DiscussionVO vo = discussionService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取讨论列表")
    @GetMapping("/list")
    public CommonResult<ListResult<DiscussionVO>> getDiscussions(@RequestParam(defaultValue = "1") Integer current,
                                                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                                                 DiscussionQuery param) {

        List<DiscussionVO> list = discussionService.queryAll(current, pageSize, param);
        // 将讨论区列表转换为树形结构
        List<DiscussionVO> tree = discussionService.convertToTree(list);
        int total = tree.size();
        return CommonResult.success(new ListResult<>(tree, total));
    }

    @Operation(summary = "获取我的讨论列表")
    @GetMapping("/list-self")
    @PermissionAuthorize
    public CommonResult<ListResult<DiscussionVO>> getMyDiscussions(@RequestParam(defaultValue = "1") Integer current,
                                                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                                                             DiscussionQuery param) {
        param.setUserId(AuthUtils.getCurrentUserId());
        List<DiscussionVO> list = discussionService.queryAll(current, pageSize, param);
        // 将讨论区列表转换为树形结构
        List<DiscussionVO> tree = discussionService.convertToTree(list);
        int total = tree.size();
        return CommonResult.success(new ListResult<>(tree, total));
    }

    /**
     * 添加讨论区
     *
     * @param courseId 课程id
     * @param param    讨论区信息
     * @return 是否添加成功
     */
    @Operation(summary = "添加讨论")
    @PostMapping("/add/{courseId}")
    public CommonResult<Boolean> addDiscussion(@PathVariable Long courseId, @RequestBody Discussion param) {
        // 检查课程是否存在
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getCourseId, courseId);
        AssertUtils.isTrue(courseService.exists(wrapper), HttpStatus.NOT_FOUND, "课程不存在");

        // 设置课程id和用户id
        param.setCourseId(courseId);
        param.setUserId(AuthUtils.getCurrentUserId());
        // 保存讨论区
        return discussionService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定讨论区信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateDiscussion(@PathVariable Long id,
                                                  @RequestBody Discussion param) {
        param.setDiscussionId(id);
        return discussionService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定讨论")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteDiscussion(@PathVariable Long id) {
        return discussionService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }
}
