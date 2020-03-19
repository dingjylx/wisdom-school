package com.wisdom.classroom.service;

import com.wisdom.classroom.entity.WisdomSay;
import com.wisdom.classroom.vo.QuestionVO;
import com.wisdom.classroom.vo.WisdomSayVO;

import java.util.List;


public interface WisdomSayService extends IService<WisdomSay> {

    List<WisdomSayVO> selectSayList();

}