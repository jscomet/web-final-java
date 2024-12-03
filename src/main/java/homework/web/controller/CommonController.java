package homework.web.controller;

import homework.web.constant.enums.UploadModule;
import homework.web.property.AppProperty;
import homework.web.util.UploadUtils;
import homework.web.util.beans.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 通用控制其
 *
 * @author 30597
 * @since 2024-12-03 8:20
 */
@Tag(name = "CommonController", description = "通用控制器")
@RestController
@RequestMapping("/common")
public class CommonController {
    @Resource
    private AppProperty appProperty;
    /**
     * 上传图片
     *
     * @param image 图片
     * @return 图片地址
     */
    @Operation(summary = "上传图片")
    @PostMapping("/upload/image")
    public CommonResult<String> uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        // 上传图片
        String filename= UploadUtils.upload(image, UploadModule.IMAGE.toString());
        String url = appProperty.getBack().getUrl() + "/common/view/image?filename=" + filename;
        return CommonResult.success(url);
    }
    /**
     * 查看图片
     */
    @Operation(summary = "查看图片")
    @GetMapping("/view/image")
    public ResponseEntity<org.springframework.core.io.Resource> viewImage(String filename, HttpServletRequest response) throws IOException {

        return UploadUtils.getResponseEntity( UploadModule.IMAGE.toString(),filename);
    }
    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件地址
     */
    @Operation(summary = "上传文件")
    @PostMapping("/upload/file")
    public CommonResult<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        // 上传文件
        String filename= UploadUtils.upload(file, UploadModule.FILE.toString());
        String url = appProperty.getBack().getUrl() + "/common/view/file?filename=" + filename;
        return CommonResult.success(url);
    }
    /**
     * 查看文件
     */
    @Operation(summary = "查看文件")
    @GetMapping("/view/file")
    public ResponseEntity<org.springframework.core.io.Resource> viewFile(String filename, HttpServletRequest response) throws IOException {

        return UploadUtils.getResponseEntity( UploadModule.FILE.toString(),filename);
    }
}
