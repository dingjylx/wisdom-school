package com.wisdom.classroom.mapper;

import com.wisdom.classroom.entity.WisdomMenu;

import java.util.HashMap;
import java.util.List;

import com.wisdom.classroom.util.MyMapper;

public interface WisdomMenuMapper extends MyMapper<WisdomMenu> {

    List<WisdomMenu> selectMenusByRoleId(Integer roleid);

    List<WisdomMenu> selectByParentIdAndRoleId(HashMap<String, Object> paraMap);

    Integer selectTopClassMenuId(Integer roleId);
}