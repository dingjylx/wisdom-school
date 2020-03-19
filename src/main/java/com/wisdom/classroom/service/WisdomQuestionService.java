package com.wisdom.classroom.service;

import com.wisdom.classroom.entity.WisdomClass;
import com.wisdom.classroom.entity.WisdomQuestion;
import com.wisdom.classroom.vo.QuestionVO;

import java.util.List;


public interface WisdomQuestionService extends IService<WisdomQuestion> {

    List<QuestionVO> selectQuestionList(String title);

}