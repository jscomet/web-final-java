package homework.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import homework.web.annotation.PermissionAuthorize;
import homework.web.config.valid.QueryGroup;
import homework.web.entity.dto.DiscussionForm;
import homework.web.entity.dto.DiscussionQuery;
import homework.web.entity.dto.DiscussionReplyForm;
import homework.web.entity.dto.DiscussionReplyQuery;
import homework.web.entity.po.Course;
import homework.web.entity.po.Discussion;
import homework.web.entity.po.DiscussionReply;
import homework.web.entity.vo.DiscussionReplyWithSubVO;
import homework.web.entity.vo.DiscussionVO;
import homework.web.entity.vo.DiscussionWithReplyVO;
import homework.web.exception.HttpErrorException;
import homework.web.service.CourseService;
import homework.web.service.DiscussionReplyService;
import homework.web.service.DiscussionService;
import homework.web.util.AssertUtils;
import homework.web.util.AuthUtils;
import homework.web.util.beans.CommonResult;
import homework.web.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
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
    @Resource
    private DiscussionReplyService discussionReplyService;

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
        int total = discussionService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }


    @Operation(summary = "获取我的讨论预览列表")
    @GetMapping("/list-my-detail")
    @PermissionAuthorize
    public CommonResult<ListResult<DiscussionWithReplyVO>> getMyDiscussions(@RequestParam(defaultValue = "1") Integer current,
                                                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                                                            DiscussionQuery param) {
        param.setUserId(AuthUtils.getCurrentUserId());
        List<DiscussionVO> baseList = discussionService.queryAll(current, pageSize, param);
        // 转换为带回复的VO
        List<DiscussionWithReplyVO> list = baseList.stream().map(base -> {
            DiscussionWithReplyVO vo = new DiscussionWithReplyVO();
            BeanUtils.copyProperties(base, vo);

            DiscussionReplyQuery query = new DiscussionReplyQuery();
            query.setDiscussionId(base.getDiscussionId());
            vo.setReplyList(discussionReplyService.queryAll(1, 3, query));
            vo.setReplyCount(discussionReplyService.count(query));
            return vo;
        }).toList();

        int total = discussionService.count(param);

        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "根据讨论ID查询讨论回复，一级评论加上其子评论")
    @GetMapping("/list-replies")
    @PermissionAuthorize
    public CommonResult<ListResult<DiscussionReplyWithSubVO>> getDiscussionReplies(@RequestParam(defaultValue = "1") Integer current,
                                                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                                                   @Validated(QueryGroup.class) DiscussionReplyQuery param) {

        List<DiscussionReplyWithSubVO> list = discussionReplyService.queryAll(current, pageSize, param);
        int total = discussionReplyService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }


    @Operation(summary = "用户添加讨论")
    @PostMapping("/add-discussion")
    public CommonResult<Boolean> addDiscussion(@RequestBody @Validated DiscussionForm form) {
        // 检查课程是否存在
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getCourseId, form.getCourseId());
        AssertUtils.isTrue(courseService.exists(wrapper), HttpStatus.NOT_FOUND, "课程不存在");
        Discussion param = new Discussion();
        // 设置课程id和用户id
        param.setCourseId(param.getCourseId());
        param.setUserId(AuthUtils.getCurrentUserId());
        param.setTitle(form.getTitle());
        param.setContent(form.getContent());
        // 保存讨论区
        return discussionService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "添加回复")
    @PostMapping("/add-reply")
    public CommonResult<Boolean> addReply(@RequestBody @Validated DiscussionReplyForm form) {
        // 如果是回复
        DiscussionReply param = new DiscussionReply();
        if (form.getReplyId() != null) {
            // 检查回复是否存在
            DiscussionReply reply = discussionReplyService.getById(form.getReplyId());
            AssertUtils.notNull(reply, HttpStatus.NOT_FOUND, "回复不存在");

            param.setDiscussionId(reply.getDiscussionId());
            param.setParentId(reply.getReplyId());
            param.setContent(form.getContent());
            param.setUserId(AuthUtils.getCurrentUserId());
        }
        // 如果是讨论
        else if(form.getDiscussionId() != null) {
            // 检查讨论是否存在
            Discussion discussion = discussionService.getById(form.getDiscussionId());
            AssertUtils.notNull(discussion, HttpStatus.NOT_FOUND, "讨论不存在");
            param.setDiscussionId(form.getDiscussionId());
            param.setContent(form.getContent());
            param.setParentId(0L);
            param.setUserId(AuthUtils.getCurrentUserId());
        }else{
            throw new HttpErrorException(HttpStatus.BAD_REQUEST,"讨论ID和回复Id不能都为空");
        }
        return  CommonResult.success(discussionReplyService.save(param)) ;
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
