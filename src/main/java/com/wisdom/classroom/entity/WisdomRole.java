package com.wisdom.classroom.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * wisdom_role
 * 通过 MyBatis Generator 生成的代码,不要修改 2020-02-21
 */
public class WisdomRole implements Serializable {
    /**
     *
     */
    @Id
    @GeneratedValue(generator = "JDBC")//此处加上注解
    private Integer id;

    /**
     *
     */
    private String bz;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String remarks;

    /**
     * wisdom_role
     */
    private static final long serialVersionUID = 1L;

    public static final transient String ID = "id";

    public static final transient String BZ = "bz";

    public static final transient String NAME = "name";

    public static final transient String REMARKS = "remarks";

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return bz
     */
    public String getBz() {
        return bz;
    }

    /**
     * @param bz
     */
    public void setBz(String bz) {
        this.bz = bz;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        sb.append(", bz=").append(bz);
        sb.append(", name=").append(name);
        sb.append(", remarks=").append(remarks);
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
        WisdomRole other = (WisdomRole) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getBz() == null ? other.getBz() == null : this.getBz().equals(other.getBz()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()));
    }

    /**
     * @mbg.generated 2020-02-21
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBz() == null) ? 0 : getBz().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
        return result;
    }
}