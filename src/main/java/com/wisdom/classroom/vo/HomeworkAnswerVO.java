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
public class HomeworkAnswerVO implements Serializable {
    private static final long serialVersionUID = -6721101957811197188L;

    private Long answerId;

    private Long homeworkId;

    private String studentName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String answerFileName;

}
