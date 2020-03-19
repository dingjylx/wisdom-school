package com.wisdom.classroom.service.impl;

import com.wisdom.classroom.entity.WisdomUser;
import com.wisdom.classroom.mapper.WisdomUserMapper;
import com.wisdom.classroom.service.WisdomUserService;
import com.wisdom.classroom.vo.WisdomUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WisdomUserServiceImpl extends BaseService<WisdomUser> implements WisdomUserService {

    @Autowired
    private WisdomUserMapper mapper;

    @Override
    public WisdomUserVO selectUserInfo(Long userId) {
        return mapper.selectUserInfo(userId);
    }
}
