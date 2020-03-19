package com.wisdom.classroom.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisdom.classroom.entity.WisdomComment;
import com.wisdom.classroom.entity.WisdomQuestion;
import com.wisdom.classroom.entity.WisdomUser;
import com.wisdom.classroom.service.WisdomCommentService;
import com.wisdom.classroom.service.WisdomQuestionService;
import com.wisdom.classroom.service.WisdomUserService;
import com.wisdom.classroom.util.PageResult;
import com.wisdom.classroom.vo.CommentVO;
import com.wisdom.classroom.vo.QuestionVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/qa")
public class QuestionController {

    private static final Logger logger = LoggerFactory
            .getLogger(QuestionController.class);

    @Resource
    private WisdomQuestionService wisdomQuestionService;

    @Resource
    private WisdomCommentService wisdomCommentService;

    @RequestMapping("/main")
    @RequiresPermissions(value = {"问答专区"})
    public String main() {
        return "question/main";
    }

    @ResponseBody
    @RequestMapping(value = "/question/list")
    public PageResult list(Integer pageNum, Integer pageSize, String title, HttpSession session) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(WisdomQuestion.class);
        if (StringUtils.isNotEmpty(title)) {
            example.and().andLike("title", title);
        }
        List<QuestionVO> list = wisdomQuestionService.selectQuestionList(title);
        WisdomUser sessionUser = (WisdomUser) session.getAttribute("currentUser");
        for(QuestionVO vo : list){
            vo.setIsMine(vo.getCreatorId().equals(sessionUser.getId()));
        }
        //将查询到的数据封装到PageInfo对象
        PageInfo<QuestionVO> pageInfo = new PageInfo(list);
        PageResult pageResult = new PageResult();
        pageResult.setCode(0);
        pageResult.setMsg("");
        pageResult.setData(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    @ResponseBody
    @PostMapping("/question/add")
    public Map<String, Object> add(WisdomQuestion wisdomQuestion, HttpSession session) throws Exception {
        logger.info(JSONObject.toJSONString(wisdomQuestion));
        Map<String, Object> map = new HashMap<>();
        WisdomUser sessionUser = (WisdomUser) session.getAttribute("currentUser");
        wisdomQuestion.setCreator(sessionUser.getId());
        int i = wisdomQuestionService.saveNotNull(wisdomQuestion);
        if (i == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

    @ResponseBody
    @PostMapping("/question/update")
    public Map<String, Object> update(WisdomQuestion wisdomQuestion) throws Exception {
        logger.info(JSONObject.toJSONString(wisdomQuestion));
        Map<String, Object> map = new HashMap<>();
        int i = wisdomQuestionService.updateNotNull(wisdomQuestion);
        if (i == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

    @ResponseBody
    @PostMapping("/question/del")
    public Map<String, Object> del(Long id) throws Exception {
        logger.info("删除id=" + id);
        Map<String, Object> map = new HashMap<>();
        Example example = new Example(WisdomQuestion.class);
        example.and().andEqualTo("id", id);
        int i = wisdomQuestionService.deleteByExample(example);
        if (i == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

    @ResponseBody
    @PostMapping("/question/like")
    public Map<String, Object> like(Long id) throws Exception {
        logger.info("点赞id=" + id);
        Map<String, Object> map = new HashMap<>();
        Example example = new Example(WisdomQuestion.class);
        example.and().andEqualTo("id", id);
        List<WisdomQuestion> list = wisdomQuestionService.selectByExample(example);
        WisdomQuestion question = list.get(0);
        question.setLikeCount(question.getLikeCount() + 1);
        int i = wisdomQuestionService.updateNotNull(question);
        if (i == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/queryComments")
    public PageResult queryComments(Integer pageNum, Integer pageSize, Long id) {
        PageHelper.startPage(pageNum, pageSize);
        List<CommentVO> list = wisdomCommentService.selectCommentList(id);
        //将查询到的数据封装到PageInfo对象
        PageInfo<CommentVO> pageInfo = new PageInfo(list);
        PageResult pageResult = new PageResult();
        pageResult.setCode(0);
        pageResult.setMsg("");
        pageResult.setData(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    @ResponseBody
    @PostMapping("/comment/add")
    public Map<String, Object> addComment(WisdomComment wisdomComment, HttpSession session) throws Exception {
        logger.info(JSONObject.toJSONString(wisdomComment));
        Map<String, Object> map = new HashMap<>();
        WisdomUser sessionUser = (WisdomUser) session.getAttribute("currentUser");
        wisdomComment.setCommentator(sessionUser.getId());
        int i = wisdomCommentService.saveNotNull(wisdomComment);
        // 更新评论数
        WisdomQuestion question = wisdomQuestionService.selectByKey(wisdomComment.getQuestionId());
        question.setCommentCount(question.getCommentCount() + 1);
        int i2 = wisdomQuestionService.updateNotNull(question);

        if (i == 1 && i2 == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

}
