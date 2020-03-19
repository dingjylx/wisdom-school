package com.wisdom.classroom.service.impl;

import com.wisdom.classroom.entity.WisdomRole;
import com.wisdom.classroom.mapper.WisdomRoleMapper;
import com.wisdom.classroom.service.WisdomRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WisdomRoleServiceImpl extends BaseService<WisdomRole> implements WisdomRoleService {
    @Autowired
    private WisdomRoleMapper wisdomRoleMapper;

    @Override
    public List<WisdomRole> selectRolesByUserId(Long userid) {
        return wisdomRoleMapper.selectRolesByUserId(userid);
    }
}
