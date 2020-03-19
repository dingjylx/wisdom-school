package com.wisdom.classroom.service;

import com.wisdom.classroom.entity.WisdomUser;
import com.wisdom.classroom.vo.WisdomUserVO;

import java.util.List;

public interface WisdomUserService extends IService<WisdomUser> {

    WisdomUserVO selectUserInfo(Long userId);

}