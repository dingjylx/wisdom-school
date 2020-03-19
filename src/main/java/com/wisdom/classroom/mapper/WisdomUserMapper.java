package com.wisdom.classroom.mapper;

import com.wisdom.classroom.entity.WisdomUser;

import com.wisdom.classroom.util.MyMapper;
import com.wisdom.classroom.vo.WisdomUserVO;

public interface WisdomUserMapper extends MyMapper<WisdomUser> {

    WisdomUserVO selectUserInfo(Long userId);
}