package com.wisdom.classroom.service.impl;

import com.wisdom.classroom.entity.WisdomComment;
import com.wisdom.classroom.mapper.WisdomCommentMapper;
import com.wisdom.classroom.service.WisdomCommentService;
import com.wisdom.classroom.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WisdomCommentServiceImpl extends BaseService<WisdomComment> implements WisdomCommentService {


    @Autowired
    private WisdomCommentMapper mapper;

    @Override
    public List<CommentVO> selectCommentList(Long questionId) {
        return mapper.selectCommentList(questionId);
    }
}
