package com.wisdom.classroom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * wisdom_notice
 * 通过 MyBatis Generator 生成的代码,不要修改 2020-02-21
 */
public class WisdomNotice implements Serializable {
    /**
     *
     */
    @Id
    @GeneratedValue(generator = "JDBC")//此处加上注解
    private Long id;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 发布者
     */
    private Long userId;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     * wisdom_notice
     */
    private static final long serialVersionUID = 1L;

    public static final transient String ID = "id";

    public static final transient String NOTICE_CONTENT = "noticeContent";

    public static final transient String USER_ID = "userId";

    public static final transient String CREATE_TIME = "createTime";

    public static final transient String UPDATE_TIME = "updateTime";

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 公告内容
     *
     * @return noticeContent 公告内容
     */
    public String getNoticeContent() {
        return noticeContent;
    }

    /**
     * 公告内容
     *
     * @param noticeContent 公告内容
     */
    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    /**
     * 发布者
     *
     * @return userId 发布者
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 发布者
     *
     * @param userId 发布者
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @mbg.generated 2020-02-21
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", noticeContent=").append(noticeContent);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }

    /**
     * @mbg.generated 2020-02-21
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        WisdomNotice other = (WisdomNotice) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getNoticeContent() == null ? other.getNoticeContent() == null : this.getNoticeContent().equals(other.getNoticeContent()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    /**
     * @mbg.generated 2020-02-21
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getNoticeContent() == null) ? 0 : getNoticeContent().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}