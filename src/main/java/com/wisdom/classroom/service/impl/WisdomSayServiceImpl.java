package com.wisdom.classroom.service.impl;

import com.wisdom.classroom.entity.WisdomSay;
import com.wisdom.classroom.mapper.WisdomSayMapper;
import com.wisdom.classroom.service.WisdomSayService;
import com.wisdom.classroom.vo.WisdomSayVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WisdomSayServiceImpl extends BaseService<WisdomSay> implements WisdomSayService {

    @Autowired
    private WisdomSayMapper mapper;

    @Override
    public List<WisdomSayVO> selectSayList() {
        return mapper.selectSayList();
    }
}
