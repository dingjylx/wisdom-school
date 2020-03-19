package com.wisdom.classroom.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisdom.classroom.entity.*;
import com.wisdom.classroom.entity.WisdomSay;
import com.wisdom.classroom.service.WisdomCommentService;
import com.wisdom.classroom.service.WisdomQuestionService;
import com.wisdom.classroom.service.WisdomSayCommentService;
import com.wisdom.classroom.service.WisdomSayService;
import com.wisdom.classroom.util.PageResult;
import com.wisdom.classroom.vo.CommentVO;
import com.wisdom.classroom.vo.QuestionVO;
import com.wisdom.classroom.vo.WisdomSayVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/myspace")
public class MyspaceController {

    private static final Logger logger = LoggerFactory
            .getLogger(MyspaceController.class);

    @Resource
    private WisdomSayService wisdomSayService;

    @Resource
    private WisdomSayCommentService wisdomSayCommentService;

    @RequestMapping("/main")
    @RequiresPermissions(value = {"我的空间"})
    public String main() {
        return "myspace/main";
    }

    @ResponseBody
    @RequestMapping(value = "/querySayList")
    public PageResult querySayList(Integer pageNum, Integer pageSize, String title) {
        PageHelper.startPage(pageNum, pageSize);
        List<WisdomSayVO> list = wisdomSayService.selectSayList();
        //将查询到的数据封装到PageInfo对象
        PageInfo<WisdomSayVO> pageInfo = new PageInfo(list);
        PageResult pageResult = new PageResult();
        pageResult.setCode(0);
        pageResult.setMsg("");
        pageResult.setData(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    @ResponseBody
    @PostMapping("/publish")
    public Map<String, Object> add(WisdomSay wisdomSay, HttpSession session) throws Exception {
        logger.info(JSONObject.toJSONString(wisdomSay));
        Map<String, Object> map = new HashMap<>();
        WisdomUser sessionUser = (WisdomUser) session.getAttribute("currentUser");
        wisdomSay.setUserId(sessionUser.getId());
        int i = wisdomSayService.saveNotNull(wisdomSay);
        if (i == 1) {
            map.put("success", true);
            map.put("msg", "发布成功！");
        } else {
            map.put("success", false);
            map.put("msg", "发布失败！");
        }
        return map;
    }

    @ResponseBody
    @PostMapping("/del")
    public Map<String, Object> del(Long id) throws Exception {
        logger.info("删除id=" + id);
        Map<String, Object> map = new HashMap<>();
        Example example = new Example(WisdomSay.class);
        example.and().andEqualTo("id", id);
        int i = wisdomSayService.deleteByExample(example);
        if (i == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

    @RequestMapping("/viewComments")
    public String viewComments(Long sayId, Model model) {
        model.addAttribute("sayId", sayId);
        return "myspace/comment";
    }

    @ResponseBody
    @RequestMapping(value = "/queryComments")
    public PageResult queryComments(Integer pageNum, Integer pageSize, Long sayId) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(WisdomSayComment.class);
        example.and().andEqualTo("sayId", sayId);
        example.setOrderByClause("send_time desc");
        List<WisdomSayComment> list = wisdomSayCommentService.selectByExample(example);
        //将查询到的数据封装到PageInfo对象
        PageInfo<WisdomSayComment> pageInfo = new PageInfo(list);
        PageResult pageResult = new PageResult();
        pageResult.setCode(0);
        pageResult.setMsg("");
        pageResult.setData(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    @ResponseBody
    @PostMapping("/like")
    public Map<String, Object> like(Long id) throws Exception {
        logger.info("点赞id=" + id);
        Map<String, Object> map = new HashMap<>();
        Example example = new Example(WisdomSay.class);
        example.and().andEqualTo("id", id);
        List<WisdomSay> list = wisdomSayService.selectByExample(example);
        WisdomSay wisdomSay = list.get(0);
        wisdomSay.setLikeCount(wisdomSay.getLikeCount() + 1);
        int i = wisdomSayService.updateNotNull(wisdomSay);
        if (i == 1) {
            map.put("success", true);
            map.put("msg", "已点赞！");
        } else {
            map.put("success", false);
            map.put("msg", "系统异常！");
        }
        return map;
    }

    @ResponseBody
    @PostMapping("/replyComment")
    public Map<String, Object> replyComment(WisdomSayComment sayComment, HttpSession session) throws Exception {
        logger.info(JSONObject.toJSONString(sayComment));
        Map<String, Object> map = new HashMap<>();
        WisdomUser sessionUser = (WisdomUser) session.getAttribute("currentUser");
        sayComment.setOwner(sessionUser.getUserName());
        wisdomSayCommentService.saveNotNull(sayComment);

        // 更新说说的评论数
        WisdomSay wisdomSay = wisdomSayService.selectByKey(sayComment.getSayId());
        wisdomSay.setCommentCount(wisdomSay.getCommentCount() + 1);
        int i = wisdomSayService.updateNotNull(wisdomSay);
        if (i == 1) {
            map.put("success", true);
            map.put("msg", "回复成功！");
        } else {
            map.put("success", false);
            map.put("msg", "系统异常！");
        }
        return map;
    }


}
