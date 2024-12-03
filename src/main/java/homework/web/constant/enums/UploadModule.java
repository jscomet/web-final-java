package homework.web.constant.enums;

/**
 * 上传模块
 *
 */
public enum UploadModule {
    COMMENT, IMAGE, FILE;

    @Override
    public String toString() {
        return super.name().toLowerCase();
    }
}
