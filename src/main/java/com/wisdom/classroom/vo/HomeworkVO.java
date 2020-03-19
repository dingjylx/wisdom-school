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
public class HomeworkVO implements Serializable {
    private static final long serialVersionUID = -6721101957811197188L;

    private Long id;

    private String homeworkName;

    /**
     * 作业文件ID
     */
    private Long fileId;

    /**
     * 上传作业的老师用户
     */
    private String teacherName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String fileName;

}
