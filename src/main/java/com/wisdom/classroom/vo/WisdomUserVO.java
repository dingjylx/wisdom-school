package com.wisdom.classroom.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WisdomUserVO implements Serializable {

    private Long id;

    private String mobile;

    private String password;

    private String userName;

    private String userType;

    private Long classId;

    private String className;

    private String studentNo;

    private String college;

    private String subject;

    private Date createTime;

    private Date updateTime;

}