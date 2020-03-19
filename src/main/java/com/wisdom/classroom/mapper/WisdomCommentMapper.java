package com.wisdom.classroom.mapper;

import com.wisdom.classroom.entity.WisdomComment;
import com.wisdom.classroom.util.MyMapper;
import com.wisdom.classroom.vo.CommentVO;

import java.util.List;

public interface WisdomCommentMapper extends MyMapper<WisdomComment> {

    List<CommentVO> selectCommentList(Long questionId);

}