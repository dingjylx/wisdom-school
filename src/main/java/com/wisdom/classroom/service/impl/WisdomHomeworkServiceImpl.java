package com.wisdom.classroom.service.impl;

import com.wisdom.classroom.entity.WisdomClass;
import com.wisdom.classroom.entity.WisdomHomework;
import com.wisdom.classroom.mapper.WisdomHomeworkMapper;
import com.wisdom.classroom.service.WisdomClassService;
import com.wisdom.classroom.service.WisdomHomeworkService;
import com.wisdom.classroom.vo.HomeworkAnswerVO;
import com.wisdom.classroom.vo.HomeworkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WisdomHomeworkServiceImpl extends BaseService<WisdomHomework> implements WisdomHomeworkService {

    @Autowired
    private WisdomHomeworkMapper mapper;

    @Override
    public List<HomeworkVO> selectHomeworkList(String homeworkName) {
        return mapper.selectHomeworkList(homeworkName);
    }

    @Override
    public List<HomeworkAnswerVO> selectHomeworkAnswerList(Long homeworkId) {
        return mapper.selectHomeworkAnswerList(homeworkId);
    }
}
