package com.wisdom.classroom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * wisdom_attendance_code
 * 通过 MyBatis Generator 生成的代码,不要修改 2020-02-23
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WisdomAttendanceCode implements Serializable {
    /**
     *
     */
    @Id
    @GeneratedValue(generator = "JDBC")//此处加上注解
    private Long codeId;

    /**
     * 考勤码
     */
    private String code;

    /**
     * 考勤日期
     */
    private String attendanceTime;

    /**
     * 考勤码过期时间（默认生成后1小时过期）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;

    /**
     * 生成日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * wisdom_attendance_code
     */
    private static final long serialVersionUID = 1L;

}