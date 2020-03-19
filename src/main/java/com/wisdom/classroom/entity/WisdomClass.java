package com.wisdom.classroom.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * wisdom_class
 * 通过 MyBatis Generator 生成的代码,不要修改 2020-02-21
 */
public class WisdomClass implements Serializable {
    /**
     *
     */
    @Id
    @GeneratedValue(generator = "JDBC")//此处加上注解
    private Long classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 邀请码
     */
    private String invitationCode;

    /**
     * 创建用户ID
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
     * wisdom_class
     */
    private static final long serialVersionUID = 1L;

    public static final transient String CLASS_ID = "classId";

    public static final transient String CLASS_NAME = "className";

    public static final transient String INVITATION_CODE = "invitationCode";

    public static final transient String USER_ID = "userId";

    public static final transient String CREATE_TIME = "createTime";

    public static final transient String UPDATE_TIME = "updateTime";

    /**
     * @return classId
     */
    public Long getClassId() {
        return classId;
    }

    /**
     * @param classId
     */
    public void setClassId(Long classId) {
        this.classId = classId;
    }

    /**
     * 班级名称
     *
     * @return className 班级名称
     */
    public String getClassName() {
        return className;
    }

    /**
     * 班级名称
     *
     * @param className 班级名称
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 邀请码
     *
     * @return invitationCode 邀请码
     */
    public String getInvitationCode() {
        return invitationCode;
    }

    /**
     * 邀请码
     *
     * @param invitationCode 邀请码
     */
    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    /**
     * 创建用户ID
     *
     * @return userId 创建用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 创建用户ID
     *
     * @param userId 创建用户ID
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
        sb.append(", classId=").append(classId);
        sb.append(", className=").append(className);
        sb.append(", invitationCode=").append(invitationCode);
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
        WisdomClass other = (WisdomClass) that;
        return (this.getClassId() == null ? other.getClassId() == null : this.getClassId().equals(other.getClassId()))
                && (this.getClassName() == null ? other.getClassName() == null : this.getClassName().equals(other.getClassName()))
                && (this.getInvitationCode() == null ? other.getInvitationCode() == null : this.getInvitationCode().equals(other.getInvitationCode()))
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
        result = prime * result + ((getClassId() == null) ? 0 : getClassId().hashCode());
        result = prime * result + ((getClassName() == null) ? 0 : getClassName().hashCode());
        result = prime * result + ((getInvitationCode() == null) ? 0 : getInvitationCode().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}