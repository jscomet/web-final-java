package homework.web.controller;

import homework.web.entity.dto.CourseCarouselQuery;
import homework.web.entity.po.CourseCarousel;
import homework.web.entity.vo.CourseCarouselVO;
import homework.web.service.CourseCarouselService;
import homework.web.util.beans.CommonResult;
import homework.web.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程轮播图(CourseCarousel)表控制层
 *
 * @author jscomet
 * @since 2024-12-02 21:53:50
 */
@Tag(name = "CourseCarousel", description = "课程轮播图")
@RestController
@RequestMapping("/course-carousel")
public class CourseCarouselController {
    @Resource
    private CourseCarouselService courseCarouselService;

    @Operation(summary = "获取指定课程轮播图信息")
    @GetMapping("/info/{id}")
    public CommonResult<CourseCarouselVO> getCourseCarousel(@PathVariable Long id) {
        CourseCarouselVO vo = courseCarouselService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取课程轮播图列表")
    @GetMapping("/list")
    public CommonResult<ListResult<CourseCarouselVO>> getCourseCarousels(@RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer pageSize,
            CourseCarouselQuery param) {
        List<CourseCarouselVO> list = courseCarouselService.queryAll(current, pageSize, param);
        int total = courseCarouselService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "添加课程轮播图")
    @PostMapping("/add")
    public CommonResult<Boolean> addCourseCarousel(@RequestBody CourseCarousel param) {
        return courseCarouselService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定课程轮播图信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateCourseCarousel(@PathVariable Long id,
            @RequestBody CourseCarousel param) {
            param.setCarouselId(id);
        return courseCarouselService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定课程轮播图")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteCourseCarousel(@PathVariable Long id) {
        return courseCarouselService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }
}
