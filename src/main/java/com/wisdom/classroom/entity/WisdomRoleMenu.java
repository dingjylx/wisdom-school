package com.wisdom.classroom.entity;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * wisdom_role_menu
 * 通过 MyBatis Generator 生成的代码,不要修改 2020-02-21
 */
public class WisdomRoleMenu implements Serializable {
    /**
     *
     */
    @Id
    private Integer id;

    /**
     *
     */
    private Integer menuId;

    /**
     *
     */
    private Integer roleId;

    /**
     * wisdom_role_menu
     */
    private static final long serialVersionUID = 1L;

    public static final transient String ID = "id";

    public static final transient String MENU_ID = "menuId";

    public static final transient String ROLE_ID = "roleId";

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
     * @return menuId
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * @param menuId
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    /**
     * @return roleId
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
        sb.append(", menuId=").append(menuId);
        sb.append(", roleId=").append(roleId);
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
        WisdomRoleMenu other = (WisdomRoleMenu) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getMenuId() == null ? other.getMenuId() == null : this.getMenuId().equals(other.getMenuId()))
                && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()));
    }

    /**
     * @mbg.generated 2020-02-21
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMenuId() == null) ? 0 : getMenuId().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        return result;
    }
}