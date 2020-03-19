package com.wisdom.classroom.service;

import com.wisdom.classroom.entity.WisdomMenu;

import java.util.HashMap;
import java.util.List;

public interface WisdomMenuService extends IService<WisdomMenu> {

    List<WisdomMenu> selectMenusByRoleId(Integer roleid);

    List<WisdomMenu> selectByParentIdAndRoleId(HashMap<String, Object> paraMap);

    Integer selectTopClassMenuId(Integer roleId);

}