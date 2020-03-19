package com.wisdom.classroom.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WisdomSayVO implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     * 说说发布人ID
     */
    private Long userId;

    /**
     * 说说内容
     */
    private String sayContent;

    private String userName;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendTime;

    private Integer likeCount;

    private Integer commentCount;

    /**
     * wisdom_say
     */
    private static final long serialVersionUID = 1L;

}