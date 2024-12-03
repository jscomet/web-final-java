package homework.web.util.beans;


import homework.web.util.NameTransferUtils;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.regex.Pattern;

/**
 * 排序参数
 */
public class Sorter {
    @Schema(description = "排序字段")
    private String column;
    @Schema(description = "排序方式 asc/desc")
    private String mode;
    @Schema(description = "是否自定义字段")
    private Boolean customField = false;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = NameTransferUtils.camelToSnake(column);
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Boolean getCustomField() {
        return customField;
    }

    public void setCustomField(Boolean customField) {
        this.customField = customField;
    }

    public static boolean check(Sorter sorter) {
        if (sorter == null
                || (sorter.getColumn() == null && sorter.getMode() == null)) {
            return true;
        }
        if (sorter.getColumn() != null && sorter.getMode() == null
                || sorter.getColumn() == null && sorter.getMode() != null) {
            return false;
        }
        // 判断排序方式
        if (!sorter.getMode().equalsIgnoreCase("asc")
                && !sorter.getMode().equalsIgnoreCase("desc")) {
            return false;
        }
        return Pattern.compile("^[a-zA-Z0-9_]+$").matcher(sorter.getColumn()).matches();
    }
}
