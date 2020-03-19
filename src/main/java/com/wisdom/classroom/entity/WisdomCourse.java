package com.wisdom.classroom.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * wisdom_course
 * 通过 MyBatis Generator 生成的代码,不要修改 2020-02-21
 */
public class WisdomCourse implements Serializable {
    /**
     *
     */
    @Id
    @GeneratedValue(generator = "JDBC")//此处加上注解
    private Long id;

    /**
     * 课程名称
     */
    private String courseTitle;

    /**
     * 课程老师
     */
    private String courseTeacher;

    /**
     * 上课时间
     */
    private String courseTime;

    /**
     * 上课教室
     */
    private String courseClassroom;

    /**
     * 已选人数
     */
    private Integer selectedNumber;

    /**
     * 最大可选人数
     */
    private Integer maxNumber;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    /**
     * wisdom_course
     */
    private static final long serialVersionUID = 1L;

    public static final transient String ID = "id";

    public static final transient String COURSE_TITLE = "courseTitle";

    public static final transient String COURSE_TEACHER = "courseTeacher";

    public static final transient String COURSE_TIME = "courseTime";

    public static final transient String COURSE_CLASSROOM = "courseClassroom";

    public static final transient String SELECTED_NUMBER = "selectedNumber";

    public static final transient String MAX_NUMBER = "maxNumber";

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
     * 课程名称
     *
     * @return courseTitle 课程名称
     */
    public String getCourseTitle() {
        return courseTitle;
    }

    /**
     * 课程名称
     *
     * @param courseTitle 课程名称
     */
    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    /**
     * 课程老师
     *
     * @return courseTeacher 课程老师
     */
    public String getCourseTeacher() {
        return courseTeacher;
    }

    /**
     * 课程老师
     *
     * @param courseTeacher 课程老师
     */
    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    /**
     * 上课时间
     *
     * @return courseTime 上课时间
     */
    public String getCourseTime() {
        return courseTime;
    }

    /**
     * 上课时间
     *
     * @param courseTime 上课时间
     */
    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    /**
     * 上课教室
     *
     * @return courseClassroom 上课教室
     */
    public String getCourseClassroom() {
        return courseClassroom;
    }

    /**
     * 上课教室
     *
     * @param courseClassroom 上课教室
     */
    public void setCourseClassroom(String courseClassroom) {
        this.courseClassroom = courseClassroom;
    }

    /**
     * 已选人数
     *
     * @return selectedNumber 已选人数
     */
    public Integer getSelectedNumber() {
        return selectedNumber;
    }

    /**
     * 已选人数
     *
     * @param selectedNumber 已选人数
     */
    public void setSelectedNumber(Integer selectedNumber) {
        this.selectedNumber = selectedNumber;
    }

    /**
     * 最大可选人数
     *
     * @return maxNumber 最大可选人数
     */
    public Integer getMaxNumber() {
        return maxNumber;
    }

    /**
     * 最大可选人数
     *
     * @param maxNumber 最大可选人数
     */
    public void setMaxNumber(Integer maxNumber) {
        this.maxNumber = maxNumber;
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
        sb.append(", courseTitle=").append(courseTitle);
        sb.append(", courseTeacher=").append(courseTeacher);
        sb.append(", courseTime=").append(courseTime);
        sb.append(", courseClassroom=").append(courseClassroom);
        sb.append(", selectedNumber=").append(selectedNumber);
        sb.append(", maxNumber=").append(maxNumber);
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
        WisdomCourse other = (WisdomCourse) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getCourseTitle() == null ? other.getCourseTitle() == null : this.getCourseTitle().equals(other.getCourseTitle()))
                && (this.getCourseTeacher() == null ? other.getCourseTeacher() == null : this.getCourseTeacher().equals(other.getCourseTeacher()))
                && (this.getCourseTime() == null ? other.getCourseTime() == null : this.getCourseTime().equals(other.getCourseTime()))
                && (this.getCourseClassroom() == null ? other.getCourseClassroom() == null : this.getCourseClassroom().equals(other.getCourseClassroom()))
                && (this.getSelectedNumber() == null ? other.getSelectedNumber() == null : this.getSelectedNumber().equals(other.getSelectedNumber()))
                && (this.getMaxNumber() == null ? other.getMaxNumber() == null : this.getMaxNumber().equals(other.getMaxNumber()))
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
        result = prime * result + ((getCourseTitle() == null) ? 0 : getCourseTitle().hashCode());
        result = prime * result + ((getCourseTeacher() == null) ? 0 : getCourseTeacher().hashCode());
        result = prime * result + ((getCourseTime() == null) ? 0 : getCourseTime().hashCode());
        result = prime * result + ((getCourseClassroom() == null) ? 0 : getCourseClassroom().hashCode());
        result = prime * result + ((getSelectedNumber() == null) ? 0 : getSelectedNumber().hashCode());
        result = prime * result + ((getMaxNumber() == null) ? 0 : getMaxNumber().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}