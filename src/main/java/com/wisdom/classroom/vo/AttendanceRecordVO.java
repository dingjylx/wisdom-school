package com.wisdom.classroom.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRecordVO implements Serializable {
    private static final long serialVersionUID = -6721101957811197188L;

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

    private Long userId;

    private String studentName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sAttendanceTime;

    private String status;


    public String getStatusCn() {
        String statusCn = "";
        if ("Y".equals(status)) {
            statusCn = "已到";
        } else if ("N".equals(status)) {
            statusCn = "缺勤";
        } else if ("L".equals(status)) {
            statusCn = "迟到";
        }
        return statusCn;
    }

}
