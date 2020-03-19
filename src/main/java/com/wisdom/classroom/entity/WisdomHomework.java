package com.wisdom.classroom.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * wisdom_homework
 * 通过 MyBatis Generator 生成的代码,不要修改 2020-02-21
 */
public class WisdomHomework implements Serializable {
    /**
     *
     */
    @Id
    @GeneratedValue(generator = "JDBC")//此处加上注解
    private Long id;

    private String name;

    /**
     * 作业文件ID
     */
    private Long fileId;

    /**
     * 上传作业的老师用户id
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
     * wisdom_homework
     */
    private static final long serialVersionUID = 1L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
     * 作业文件ID
     *
     * @return fileId 作业文件ID
     */
    public Long getFileId() {
        return fileId;
    }

    /**
     * 作业文件ID
     *
     * @param fileId 作业文件ID
     */
    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    /**
     * 上传作业的老师用户id
     *
     * @return userId 上传作业的老师用户id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 上传作业的老师用户id
     *
     * @param userId 上传作业的老师用户id
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
        WisdomHomework other = (WisdomHomework) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
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
        result = prime * result + ((getFileId() == null) ? 0 : getFileId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}