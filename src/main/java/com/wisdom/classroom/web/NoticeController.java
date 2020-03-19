package com.wisdom.classroom.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisdom.classroom.entity.WisdomNotice;
import com.wisdom.classroom.entity.WisdomRole;
import com.wisdom.classroom.entity.WisdomUser;
import com.wisdom.classroom.service.WisdomNoticeService;
import com.wisdom.classroom.util.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    private static final Logger logger = LoggerFactory
            .getLogger(NoticeController.class);

    @Resource
    private WisdomNoticeService wisdomNoticeService;

    @RequestMapping("/main")
    @RequiresPermissions(value = {"公告管理"})
    public String main(Model model, HttpSession session) {
        model.addAttribute("sessionUser", (WisdomUser) session.getAttribute("currentUser"));
        return "notice/notice";
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public PageResult list(Integer pageNum, Integer pageSize, String noticeContent) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(WisdomNotice.class);
        if (StringUtils.isNotEmpty(noticeContent)) {
            example.createCriteria().andCondition(" notice_content like '%" + noticeContent + "%'");
        }
        example.setOrderByClause("create_time desc");
        List<WisdomNotice> list = wisdomNoticeService.selectByExample(example);
        //将查询到的数据封装到PageInfo对象
        PageInfo<WisdomNotice> pageInfo = new PageInfo(list);
        PageResult pageResult = new PageResult();
        pageResult.setCode(0);
        pageResult.setMsg("");
        pageResult.setData(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    @ResponseBody
    @PostMapping("/add")
    public Map<String, Object> add(WisdomNotice notice, HttpSession session) throws Exception {
        logger.info(JSONObject.toJSONString(notice));
        Map<String, Object> map = new HashMap<>();
        WisdomUser sessionUser = (WisdomUser) session.getAttribute("currentUser");
        notice.setUserId(sessionUser.getId());
        int i = wisdomNoticeService.saveNotNull(notice);
        if (i == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

    @ResponseBody
    @PostMapping("/update")
    public Map<String, Object> update(WisdomNotice notice) throws Exception {
        logger.info(JSONObject.toJSONString(notice));
        Map<String, Object> map = new HashMap<>();
        int i = wisdomNoticeService.updateNotNull(notice);
        if (i == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

    @ResponseBody
    @PostMapping("/del")
    public Map<String, Object> del(Long id) throws Exception {
        logger.info("删除id=" + id);
        Map<String, Object> map = new HashMap<>();
        Example example = new Example(WisdomNotice.class);
        example.and().andEqualTo("id", id);
        int i = wisdomNoticeService.deleteByExample(example);
        if (i == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

}
