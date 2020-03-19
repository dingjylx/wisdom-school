package com.wisdom.classroom.entity;

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

/**
 * wisdom_user
 * 通过 MyBatis Generator 生成的代码,不要修改 2020-02-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "wisdom_user")
public class WisdomUser implements Serializable {
    /**
     *
     */
    @Id
    @GeneratedValue(generator = "JDBC")//此处加上注解
    private Long id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户类型（T：老师，S：学生）
     */
    private String userType;

    /**
     * 班级（学生）
     */
    private Long classId;

    /**
     * 学号（学生）
     */
    private String studentNo;

    /**
     * 学院（老师）
     */
    private String college;

    /**
     * 科目（老师）
     */
    private String subject;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    @Transient
    private String roles;

    @Transient
    private String oldPassword;

}