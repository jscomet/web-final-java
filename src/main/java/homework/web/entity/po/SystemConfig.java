package homework.web.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统配置(SystemConfig)实体类
 *
 * @author jscomet
 * @since 2024-12-07 19:27:25
 */
@Data
public class SystemConfig implements Serializable {
    @Serial
    private static final long serialVersionUID = 599376328915098509L;
    /**
     * 配置名称
     */
    public enum Name {
        /**
         * 是否启用自主注册功能
         */
        ENABLE_SELF_REGISTER,
    }

    /**
     * 配置类型
     */
    public enum Type {
        /**
         * 日期范围
         */
        DATE_RANGE,
        /**
         * 日期时间范围
         */
        DATE_TIME_RANGE,
        /**
         * 布尔值
         */
        BOOLEAN,
        /**
         * 字符串
         */
        STRING,
    }

    /**
     * 配置名
     */
    @Schema(description = "配置名")    
    @TableId(value = "name",type = IdType.NONE)
    private String name;


    /**
     * 配置中文名
     */
    @Schema(description = "配置中文名")    
    @TableField(value = "cn_name")
    private String cnName;

    /**
     * 类型
     */
    @Schema(description = "类型")    
    @TableField(value = "type")
    private String type;

    /**
     * 配置内容
     */
    @Schema(description = "配置内容")
    @TableField(value = "value")
    private String value;


}
