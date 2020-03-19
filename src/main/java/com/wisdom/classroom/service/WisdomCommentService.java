package com.wisdom.classroom.service;

import com.wisdom.classroom.entity.WisdomComment;
import com.wisdom.classroom.vo.CommentVO;

import java.util.List;


public interface WisdomCommentService extends IService<WisdomComment> {

    List<CommentVO> selectCommentList(Long questionId);

}