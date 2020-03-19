package com.wisdom.classroom.mapper;

import com.wisdom.classroom.entity.WisdomQuestion;
import com.wisdom.classroom.util.MyMapper;
import com.wisdom.classroom.vo.QuestionVO;

import java.util.List;

public interface WisdomQuestionMapper extends MyMapper<WisdomQuestion> {

    List<QuestionVO> selectQuestionList(String title);

}