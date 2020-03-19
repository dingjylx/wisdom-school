package com.wisdom.classroom.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisdom.classroom.entity.WisdomCourse;
import com.wisdom.classroom.entity.WisdomUser;
import com.wisdom.classroom.service.WisdomCourseService;
import com.wisdom.classroom.util.PageResult;
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
@RequestMapping("/course")
public class CourseController {

    private static final Logger logger = LoggerFactory
            .getLogger(CourseController.class);

    @Resource
    private WisdomCourseService wisdomCourseService;

    @RequestMapping("/main")
    @RequiresPermissions(value = {"课程表管理"})
    public String main(Model model, HttpSession session) {
        model.addAttribute("sessionUser", (WisdomUser) session.getAttribute("currentUser"));
        return "course/course";
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public PageResult list(Integer pageNum, Integer pageSize, String courseTitle) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(WisdomCourse.class);
        if (StringUtils.isNotEmpty(courseTitle)) {
            example.createCriteria().andCondition(" course_title like '%" + courseTitle + "%'");
        }
        List<WisdomCourse> list = wisdomCourseService.selectByExample(example);
        //将查询到的数据封装到PageInfo对象
        PageInfo<WisdomCourse> pageInfo = new PageInfo(list);
        PageResult pageResult = new PageResult();
        pageResult.setCode(0);
        pageResult.setMsg("");
        pageResult.setData(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    @ResponseBody
    @PostMapping("/add")
    public Map<String, Object> add(WisdomCourse course, HttpSession session) throws Exception {
        logger.info(JSONObject.toJSONString(course));
        Map<String, Object> map = new HashMap<>();
        int i = wisdomCourseService.saveNotNull(course);
        if (i == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

    @ResponseBody
    @PostMapping("/update")
    public Map<String, Object> update(WisdomCourse course) throws Exception {
        logger.info(JSONObject.toJSONString(course));
        Map<String, Object> map = new HashMap<>();
        int i = wisdomCourseService.updateNotNull(course);
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
        Example example = new Example(WisdomCourse.class);
        example.and().andEqualTo("id", id);
        int i = wisdomCourseService.deleteByExample(example);
        if (i == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

}
