package com.wisdom.classroom.service;

import com.wisdom.classroom.entity.WisdomClass;
import com.wisdom.classroom.entity.WisdomHomework;
import com.wisdom.classroom.vo.HomeworkAnswerVO;
import com.wisdom.classroom.vo.HomeworkVO;

import java.util.List;


public interface WisdomHomeworkService extends IService<WisdomHomework> {

    List<HomeworkVO> selectHomeworkList(String homeworkName);

    List<HomeworkAnswerVO> selectHomeworkAnswerList(Long homeworkId);

}