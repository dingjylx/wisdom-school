package com.wisdom.classroom.service.impl;

import com.wisdom.classroom.entity.WisdomMenu;
import com.wisdom.classroom.mapper.WisdomMenuMapper;
import com.wisdom.classroom.service.WisdomMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class WisdomMenuServiceImpl extends BaseService<WisdomMenu> implements WisdomMenuService {

    @Autowired
    private WisdomMenuMapper wisdomMenuMapper;

    @Override
    public List<WisdomMenu> selectMenusByRoleId(Integer roleid) {
        return wisdomMenuMapper.selectMenusByRoleId(roleid);
    }

    @Override
    public List<WisdomMenu> selectByParentIdAndRoleId(HashMap<String, Object> paraMap) {
        return wisdomMenuMapper.selectByParentIdAndRoleId(paraMap);
    }

    @Override
    public Integer selectTopClassMenuId(Integer roleId) {
        return wisdomMenuMapper.selectTopClassMenuId(roleId);
    }
}
