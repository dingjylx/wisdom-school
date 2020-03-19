package com.wisdom.classroom.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * wisdom_homework_answer
 * 通过 MyBatis Generator 生成的代码,不要修改 2020-02-21
 */
public class WisdomHomeworkAnswer implements Serializable {
    /**
     *
     */
    @Id
    private Long id;

    /**
     * 作业ID
     */
    private Long homeworkId;

    /**
     * 上传答案的附件ID
     */
    private Long fileId;

    /**
     * 学生用户ID
     */
    private Long userId;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     * wisdom_homework_answer
     */
    private static final long serialVersionUID = 1L;

    public static final transient String ID = "id";

    public static final transient String HOMEWORK_ID = "homeworkId";

    public static final transient String FILE_ID = "fileId";

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
     * 作业ID
     *
     * @return homeworkId 作业ID
     */
    public Long getHomeworkId() {
        return homeworkId;
    }

    /**
     * 作业ID
     *
     * @param homeworkId 作业ID
     */
    public void setHomeworkId(Long homeworkId) {
        this.homeworkId = homeworkId;
    }

    /**
     * 上传答案的附件ID
     *
     * @return fileId 上传答案的附件ID
     */
    public Long getFileId() {
        return fileId;
    }

    /**
     * 上传答案的附件ID
     *
     * @param fileId 上传答案的附件ID
     */
    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    /**
     * 学生用户ID
     *
     * @return userId 学生用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 学生用户ID
     *
     * @param userId 学生用户ID
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
        sb.append(", homeworkId=").append(homeworkId);
        sb.append(", fileId=").append(fileId);
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
        WisdomHomeworkAnswer other = (WisdomHomeworkAnswer) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getHomeworkId() == null ? other.getHomeworkId() == null : this.getHomeworkId().equals(other.getHomeworkId()))
                && (this.getFileId() == null ? other.getFileId() == null : this.getFileId().equals(other.getFileId()))
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
        result = prime * result + ((getHomeworkId() == null) ? 0 : getHomeworkId().hashCode());
        result = prime * result + ((getFileId() == null) ? 0 : getFileId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}