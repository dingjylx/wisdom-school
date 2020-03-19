package com.wisdom.classroom.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * wisdom_file
 * 通过 MyBatis Generator 生成的代码,不要修改 2020-02-21
 */
@Table(name = "wisdom_file")
public class WisdomFile implements Serializable {
    /**
     *
     */
    @Id
    @GeneratedValue(generator = "JDBC")//此处加上注解
    private Long fileId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件路径
     */
    private String fileUrl;

    /**
     * 上传时间
     */
    private Date uploadTime;

    /**
     * wisdom_file
     */
    private static final long serialVersionUID = 1L;

    public static final transient String FILE_ID = "fileId";

    public static final transient String FILE_NAME = "fileName";

    public static final transient String FILE_SIZE = "fileSize";

    public static final transient String FILE_URL = "fileUrl";

    public static final transient String UPLOAD_TIME = "uploadTime";

    /**
     * @return fileId
     */
    public Long getFileId() {
        return fileId;
    }

    /**
     * @param fileId
     */
    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    /**
     * 文件名称
     *
     * @return fileName 文件名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 文件名称
     *
     * @param fileName 文件名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 文件大小
     *
     * @return fileSize 文件大小
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * 文件大小
     *
     * @param fileSize 文件大小
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 文件路径
     *
     * @return fileUrl 文件路径
     */
    public String getFileUrl() {
        return fileUrl;
    }

    /**
     * 文件路径
     *
     * @param fileUrl 文件路径
     */
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    /**
     * 上传时间
     *
     * @return uploadTime 上传时间
     */
    public Date getUploadTime() {
        return uploadTime;
    }

    /**
     * 上传时间
     *
     * @param uploadTime 上传时间
     */
    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
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
        sb.append(", fileId=").append(fileId);
        sb.append(", fileName=").append(fileName);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", fileUrl=").append(fileUrl);
        sb.append(", uploadTime=").append(uploadTime);
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
        WisdomFile other = (WisdomFile) that;
        return (this.getFileId() == null ? other.getFileId() == null : this.getFileId().equals(other.getFileId()))
                && (this.getFileName() == null ? other.getFileName() == null : this.getFileName().equals(other.getFileName()))
                && (this.getFileSize() == null ? other.getFileSize() == null : this.getFileSize().equals(other.getFileSize()))
                && (this.getFileUrl() == null ? other.getFileUrl() == null : this.getFileUrl().equals(other.getFileUrl()))
                && (this.getUploadTime() == null ? other.getUploadTime() == null : this.getUploadTime().equals(other.getUploadTime()));
    }

    /**
     * @mbg.generated 2020-02-21
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFileId() == null) ? 0 : getFileId().hashCode());
        result = prime * result + ((getFileName() == null) ? 0 : getFileName().hashCode());
        result = prime * result + ((getFileSize() == null) ? 0 : getFileSize().hashCode());
        result = prime * result + ((getFileUrl() == null) ? 0 : getFileUrl().hashCode());
        result = prime * result + ((getUploadTime() == null) ? 0 : getUploadTime().hashCode());
        return result;
    }
}