package com.wisdom.classroom.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * wisdom_attendance_record
 * 通过 MyBatis Generator 生成的代码,不要修改 2020-02-23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WisdomAttendanceRecord implements Serializable {
    /**
     *
     */
    @Id
    private Long id;

    /**
     * 考勤码id
     */
    private Long codeId;

    /**
     * 学生id
     */
    private Long userId;

    /**
     * 考勤状态（Y：已到，N：缺勤，L：迟到）
     */
    private String status;

    /**
     * 签到时间
     */
    private Date attendanceTime;

    /**
     * wisdom_attendance_record
     */
    private static final long serialVersionUID = 1L;

}