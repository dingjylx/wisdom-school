package com.wisdom.classroom.mapper;

import com.wisdom.classroom.entity.WisdomSay;
import com.wisdom.classroom.util.MyMapper;
import com.wisdom.classroom.vo.WisdomSayVO;

import java.util.List;

public interface WisdomSayMapper extends MyMapper<WisdomSay> {

    List<WisdomSayVO> selectSayList();

}