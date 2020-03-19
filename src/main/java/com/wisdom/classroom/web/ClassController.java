package com.wisdom.classroom.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisdom.classroom.entity.WisdomClass;
import com.wisdom.classroom.entity.WisdomUser;
import com.wisdom.classroom.service.WisdomClassService;
import com.wisdom.classroom.service.WisdomUserService;
import com.wisdom.classroom.util.PageResult;
import org.apache.commons.collections.CollectionUtils;
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
@RequestMapping("/class")
public class ClassController {

    private static final Logger logger = LoggerFactory
            .getLogger(ClassController.class);

    @Resource
    private WisdomClassService wisdomClassService;

    @Resource
    private WisdomUserService wisdomUserService;

    @RequestMapping("/main")
    @RequiresPermissions(value = {"课程表管理"})
    public String main(Model model, HttpSession session) {
        model.addAttribute("sessionUser", (WisdomUser) session.getAttribute("currentUser"));
        return "class/class";
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public PageResult list(Integer pageNum, Integer pageSize, String className) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(WisdomClass.class);
        if (StringUtils.isNotEmpty(className)) {
            example.createCriteria().andCondition(" class_name like '%" + className + "%'");
        }
        List<WisdomClass> list = wisdomClassService.selectByExample(example);
        //将查询到的数据封装到PageInfo对象
        PageInfo<WisdomClass> pageInfo = new PageInfo(list);
        PageResult pageResult = new PageResult();
        pageResult.setCode(0);
        pageResult.setMsg("");
        pageResult.setData(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    @ResponseBody
    @PostMapping("/add")
    public Map<String, Object> add(WisdomClass wisdomClass, HttpSession session) throws Exception {
        logger.info(JSONObject.toJSONString(wisdomClass));
        Map<String, Object> map = new HashMap<>();
        WisdomUser sessionUser = (WisdomUser) session.getAttribute("currentUser");
        wisdomClass.setUserId(sessionUser.getId());
        int i = wisdomClassService.saveNotNull(wisdomClass);
        if (i == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

    @ResponseBody
    @PostMapping("/update")
    public Map<String, Object> update(WisdomClass wisdomClass) throws Exception {
        logger.info(JSONObject.toJSONString(wisdomClass));
        Map<String, Object> map = new HashMap<>();
        int i = wisdomClassService.updateNotNull(wisdomClass);
        if (i == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

    @ResponseBody
    @PostMapping("/del")
    public Map<String, Object> del(Long classId) throws Exception {
        logger.info("删除id=" + classId);
        Map<String, Object> map = new HashMap<>();
        Example example = new Example(WisdomClass.class);
        example.and().andEqualTo("classId", classId);
        int i = wisdomClassService.deleteByExample(example);
        if (i == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/queryClassStudents")
    public PageResult queryClassStudents(Integer pageNum, Integer pageSize, Long classId) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(WisdomUser.class);
        example.and().andEqualTo("classId", classId);
        List<WisdomUser> list = wisdomUserService.selectByExample(example);
        //将查询到的数据封装到PageInfo对象
        PageInfo<WisdomUser> pageInfo = new PageInfo(list);
        PageResult pageResult = new PageResult();
        pageResult.setCode(0);
        pageResult.setMsg("");
        pageResult.setData(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    @ResponseBody
    @PostMapping("/join")
    public Map<String, Object> join(String classCode, HttpSession session) throws Exception {
        logger.info(classCode);
        Map<String, Object> map = new HashMap<>();

        // 查询班级邀请码是否存在
        Example example = new Example(WisdomClass.class);
        example.and().andEqualTo("invitationCode", classCode);
        List<WisdomClass> result = wisdomClassService.selectByExample(example);
        if (CollectionUtils.isEmpty(result)) {
            map.put("success", false);
            map.put("msg", "邀请码不存在！");
        } else {
            WisdomUser sessionUser = (WisdomUser) session.getAttribute("currentUser");
            sessionUser.setClassId(result.get(0).getClassId());
            int i = wisdomUserService.updateNotNull(sessionUser);
            if (i == 1) {
                map.put("success", true);
                map.put("msg", "加入班级成功！");
            } else {
                map.put("success", false);
                map.put("msg", "系统异常！");
            }
        }
        return map;
    }

}
