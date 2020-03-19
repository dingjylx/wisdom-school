package com.wisdom.classroom.service;

import com.wisdom.classroom.entity.WisdomRole;

import java.util.List;

public interface WisdomRoleService extends IService<WisdomRole> {

    List<WisdomRole> selectRolesByUserId(Long userid);//根据userid查询所有的角色

}