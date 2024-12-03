package homework.web.util;

import homework.web.property.AppProperty;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
public class UploadUtils {

    @Resource
    private AppProperty _appProperty;

    private static AppProperty appProperty;

    @PostConstruct
    public void init() {
        appProperty = this._appProperty;
    }

    public static String upload(MultipartFile file, String module) throws IOException {
        // 文件名：日期.UUID.原文件名.后缀
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = getExtension(file);

        String filename;
        if (file.getOriginalFilename() != null) {
            filename = date + "." + uuid + "." + file.getOriginalFilename();
            // 避免重复后缀
            if (!filename.endsWith("." + extension)) {
                filename += "." + extension;
            }
        } else {
            filename = date + "." + uuid + "." + extension;
        }

        // 上传
        upload(file, module, filename);
        return filename;
    }

    public static void upload(MultipartFile file, String module, String filename) throws IOException {
        // 判断 module 目录是否存在，不存在时创建
        String modulePath = getModulePath(module);
        File moduleDir = new File(modulePath);
        if (!moduleDir.exists()) {
            AssertUtils.isTrue(moduleDir.mkdir(), HttpStatus.INTERNAL_SERVER_ERROR, "服务器上传目录异常");
        }

        // 上传文件
        String path = getFilePath(module, filename);
        try {
            Files.copy(file.getInputStream(), new File(path).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException( "服务器保存文件异常");
        }
    }

    private static String getModulePath(String module) {
        return new File(appProperty.getBack().getUploadPath() + "/" + module).getPath();
    }

    public static String getFilePath(String module, String filename) {
        return new File(getModulePath(module) + "/" + filename).getPath();
    }

    public static ResponseEntity<org.springframework.core.io.Resource> getResponseEntity(String module, String filename) throws IOException {
        // 真实文件名
        String realFileName = filename;
        // 如果文件名符合自动生成的格式，提取原文件名
        Pattern pattern = Pattern.compile("^\\d{8}\\.\\w{32}\\..*$");
        Matcher matcher = pattern.matcher(filename);
        if (matcher.matches()) {
            realFileName = filename.substring(42);
        }

        // 缓存 header
        String ccValue = CacheControl.maxAge(7, TimeUnit.DAYS)
                .noTransform()
                .cachePublic()
                .getHeaderValue();

        // 判断文件类型
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        String type = URLConnection.getFileNameMap().getContentTypeFor(filename);
        if (type != null) {
            mediaType = MediaType.parseMediaType(type);
        }

        // 读取文件流并返回
        File file = new File(getFilePath(module, filename));
        InputStreamResource resource;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CACHE_CONTROL, ccValue)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(realFileName, StandardCharsets.UTF_8) + "\"")
                .contentLength(file.length())
                .contentType(mediaType)
                .body(resource);
    }

    private static String getExtension(MultipartFile file) {
        String extension = "";
        if (file.getOriginalFilename() != null) {
            extension = FilenameUtils.getExtension(file.getOriginalFilename());
        }
        if (StringUtils.isBlank(extension) && file.getContentType() != null) {
            extension = file.getContentType().substring(file.getContentType().lastIndexOf('/') + 1);
        }
        return extension;
    }
}
