package com.wisdom.classroom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * wisdom_say_comment
 * 通过 MyBatis Generator 生成的代码,不要修改 2020-03-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WisdomSayComment implements Serializable {
    /**
     *
     */
    @Id
    private Long id;

    /**
     * 该评论的发出者
     */
    private String owner;

    /**
     * 该评论是回复哪个人的
     */
    private String toname;

    /**
     * 评论时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendTime;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论的说说id
     */
    private Long sayId;

    /**
     * 父级评论的id（0代表一级评论）
     */
    private Long pid;

    /**
     * wisdom_say_comment
     */
    private static final long serialVersionUID = 1L;


}