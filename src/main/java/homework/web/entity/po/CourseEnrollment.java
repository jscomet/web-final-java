package homework.web.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 课程注册(CourseEnrollment)实体类
 *
 * @author jscomet
 * @since 2024-12-02 21:54:11
 */
@Data
public class CourseEnrollment implements Serializable {
    @Serial
    private static final long serialVersionUID = -56766149911116332L;

    public enum Status implements IEnum<Integer> {
        /**
         * 未开始
         */
        UNSTART(0),
        /**
         * 进行中
         */
        ONGOING(1),
        /**
         * 已完成
         */
        COMPLETED(2),
        /**
         * 已退出
         */
        QUIT(3);

        private final int value;

        @Override
        @JsonValue
        public Integer getValue() {
            return value;
        }

        Status(int value) {
            this.value = value;
        }
    }
    /**
     * 注册ID
     */
    @Schema(description = "注册ID")
    @TableId(value = "enrollment_id", type = IdType.AUTO)
    private Long enrollmentId;


    /**
     * 课程ID
     */
    @Schema(description = "课程ID")
    @TableField(value = "course_id")
    private Long courseId;

    /**
     * 学生ID
     */
    @Schema(description = "学生ID")
    @TableField(value = "student_id")
    private Long studentId;

    /**
     * 状态：0-未开始,1-进行中、2-已完成、3-已退出
     */
    @Schema(description = "状态：0-未开始,1-进行中、2-已完成、3-已退出")
    @TableField(value = "status")
    private Status status;

    /**
     * 学习进度
     */
    @Schema(description = "学习进度")
    @TableField(value = "progress")
    private Float progress;

    /**
     * 注册时间
     */
    @Schema(description = "注册时间")
    @TableField(value = "create_time")
    private LocalDateTime createTime;


}
