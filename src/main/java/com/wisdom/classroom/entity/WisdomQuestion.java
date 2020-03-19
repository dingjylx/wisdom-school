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
 * wisdom_question
 * 通过 MyBatis Generator 生成的代码,不要修改 2020-02-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WisdomQuestion implements Serializable {
    /**
     *
     */
    @Id
    @GeneratedValue(generator = "JDBC")//此处加上注解
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容描述
     */
    private String description;

    /**
     * 作者
     */
    private Long creator;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     * wisdom_question
     */
    private static final long serialVersionUID = 1L;

}