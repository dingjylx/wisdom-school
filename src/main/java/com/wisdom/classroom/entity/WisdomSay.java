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
 * wisdom_say
 * 通过 MyBatis Generator 生成的代码,不要修改 2020-03-01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WisdomSay implements Serializable {
    /**
     *
     */
    @Id
    private Long id;

    /**
     * 说说发布人ID
     */
    private Long userId;

    /**
     * 说说内容
     */
    private String sayContent;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendTime;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * wisdom_say
     */
    private static final long serialVersionUID = 1L;

}