package com.wisdom.classroom.mapper;

import com.wisdom.classroom.entity.WisdomHomework;

import com.wisdom.classroom.util.MyMapper;
import com.wisdom.classroom.vo.HomeworkAnswerVO;
import com.wisdom.classroom.vo.HomeworkVO;

import java.util.List;

public interface WisdomHomeworkMapper extends MyMapper<WisdomHomework> {

    List<HomeworkVO> selectHomeworkList(String homeworkName);

    List<HomeworkAnswerVO> selectHomeworkAnswerList(Long homeworkId);
}