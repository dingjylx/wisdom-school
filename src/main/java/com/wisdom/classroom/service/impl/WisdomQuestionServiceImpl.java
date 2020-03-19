package com.wisdom.classroom.service.impl;

import com.wisdom.classroom.entity.WisdomQuestion;
import com.wisdom.classroom.mapper.WisdomQuestionMapper;
import com.wisdom.classroom.service.WisdomQuestionService;
import com.wisdom.classroom.vo.QuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WisdomQuestionServiceImpl extends BaseService<WisdomQuestion> implements WisdomQuestionService {


    @Autowired
    private WisdomQuestionMapper mapper;

    @Override
    public List<QuestionVO> selectQuestionList(String title) {
        return mapper.selectQuestionList(title);
    }
}
