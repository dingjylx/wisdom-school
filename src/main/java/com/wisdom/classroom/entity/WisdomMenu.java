package com.wisdom.classroom.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * wisdom_menu
 * 通过 MyBatis Generator 生成的代码,不要修改 2020-02-21
 */
public class WisdomMenu implements Serializable {
    /**
     *
     */
    @Id
    @GeneratedValue(generator = "JDBC")//此处加上注解
    private Integer id;

    /**
     *
     */
    private String icon;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private Integer state;

    /**
     *
     */
    private String url;

    /**
     *
     */
    private Integer pId;

    /**
     * wisdom_menu
     */
    private static final long serialVersionUID = 1L;

    public static final transient String ID = "id";

    public static final transient String ICON = "icon";

    public static final transient String NAME = "name";

    public static final transient String STATE = "state";

    public static final transient String URL = "url";

    public static final transient String P_ID = "pId";

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
     * @return icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     */
    public void setIcon(String icon) {
        this.icon = icon;
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
     * @return state
     */
    public Integer getState() {
        return state;
    }

    /**
     * @param state
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return pId
     */
    public Integer getpId() {
        return pId;
    }

    /**
     * @param pId
     */
    public void setpId(Integer pId) {
        this.pId = pId;
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
        sb.append(", icon=").append(icon);
        sb.append(", name=").append(name);
        sb.append(", state=").append(state);
        sb.append(", url=").append(url);
        sb.append(", pId=").append(pId);
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
        WisdomMenu other = (WisdomMenu) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getIcon() == null ? other.getIcon() == null : this.getIcon().equals(other.getIcon()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
                && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
                && (this.getpId() == null ? other.getpId() == null : this.getpId().equals(other.getpId()));
    }

    /**
     * @mbg.generated 2020-02-21
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIcon() == null) ? 0 : getIcon().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getpId() == null) ? 0 : getpId().hashCode());
        return result;
    }
}