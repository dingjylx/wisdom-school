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
 * wisdom_comment
 * 通过 MyBatis Generator 生成的代码,不要修改 2020-02-25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WisdomComment implements Serializable {
    /**
     *
     */
    @Id
    private Long id;

    /**
     * 问题id
     */
    private Long questionId;

    /**
     * 评论人用户Id
     */
    private Long commentator;

    /**
     * 评论内容
     */
    private String content;

    /**
     *
     */
    private Date createTime;

    /**
     * wisdom_comment
     */
    private static final long serialVersionUID = 1L;

}