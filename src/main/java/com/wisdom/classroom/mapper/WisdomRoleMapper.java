package com.wisdom.classroom.mapper;

import com.wisdom.classroom.entity.WisdomRole;

import java.util.List;

import com.wisdom.classroom.util.MyMapper;

public interface WisdomRoleMapper extends MyMapper<WisdomRole> {

    List<WisdomRole> selectRolesByUserId(Long userid);
}