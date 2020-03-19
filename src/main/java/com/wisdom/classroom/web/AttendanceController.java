package com.wisdom.classroom.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisdom.classroom.entity.WisdomAttendanceCode;
import com.wisdom.classroom.entity.WisdomAttendanceRecord;
import com.wisdom.classroom.entity.WisdomUser;
import com.wisdom.classroom.service.WisdomAttendanceCodeService;
import com.wisdom.classroom.service.WisdomAttendanceRecordService;
import com.wisdom.classroom.util.DateUtil;
import com.wisdom.classroom.util.PageResult;
import com.wisdom.classroom.vo.AttendanceRecordVO;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    private static final Logger logger = LoggerFactory
            .getLogger(AttendanceController.class);

    @Resource
    private WisdomAttendanceCodeService wisdomAttendanceCodeService;

    @Resource
    private WisdomAttendanceRecordService wisdomAttendanceRecordService;

    @RequestMapping("/code/main")
    @RequiresPermissions(value = {"考勤码管理"})
    public String main() {
        return "attendance/code";
    }

    @RequestMapping("/record/main")
    @RequiresPermissions(value = {"考勤管理"})
    public String record() {
        return "attendance/record";
    }

    @ResponseBody
    @RequestMapping(value = "/code/list")
    public PageResult list(Integer pageNum, Integer pageSize, String attendanceTime) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(WisdomAttendanceCode.class);
        if (StringUtils.isNotEmpty(attendanceTime)) {
            example.and().andEqualTo("attendanceTime", attendanceTime);
        }
        example.setOrderByClause("create_time desc");
        List<WisdomAttendanceCode> list = wisdomAttendanceCodeService.selectByExample(example);
        //将查询到的数据封装到PageInfo对象
        PageInfo<WisdomAttendanceCode> pageInfo = new PageInfo(list);
        PageResult pageResult = new PageResult();
        pageResult.setCode(0);
        pageResult.setMsg("");
        pageResult.setData(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    @ResponseBody
    @PostMapping("/code/add")
    public Map<String, Object> add(WisdomAttendanceCode code, HttpSession session) throws Exception {
        logger.info(JSONObject.toJSONString(code));
        Map<String, Object> map = new HashMap<>();
        // 默认考勤码一小时过期
        Date now = new Date();
        code.setCreateTime(now);
        code.setExpireTime(DateUtil.addHour(now, 1));
        wisdomAttendanceCodeService.saveNotNull(code);

        //初始化记录表
        int i = wisdomAttendanceRecordService.batchInitRecord(code.getCodeId());
        if (i > 0) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/code/queryRecord")
    public PageResult queryRecord(Integer pageNum, Integer pageSize, Long codeId) {
        PageHelper.startPage(pageNum, pageSize);
        List<AttendanceRecordVO> list = wisdomAttendanceRecordService.selectAttendanceRecord(codeId);
        //将查询到的数据封装到PageInfo对象
        PageInfo<AttendanceRecordVO> pageInfo = new PageInfo(list);
        PageResult pageResult = new PageResult();
        pageResult.setCode(0);
        pageResult.setMsg("");
        pageResult.setData(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    /**
     * 学生查看考勤记录
     *
     * @param pageNum
     * @param pageSize
     * @param attendanceTime
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/record/list")
    public PageResult recordList(Integer pageNum, Integer pageSize, String attendanceTime, HttpSession session) {
        PageHelper.startPage(pageNum, pageSize);
        WisdomUser sessionUser = (WisdomUser) session.getAttribute("currentUser");
        List<AttendanceRecordVO> list = wisdomAttendanceRecordService.selectAttendanceRecordByUserId(sessionUser.getId(), attendanceTime);
        //将查询到的数据封装到PageInfo对象
        PageInfo<AttendanceRecordVO> pageInfo = new PageInfo(list);
        PageResult pageResult = new PageResult();
        pageResult.setCode(0);
        pageResult.setMsg("");
        pageResult.setData(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    /**
     * 学生签到,默认只能签到当天
     *
     * @param code
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/record/add")
    public Map<String, Object> addRecord(String code, HttpSession session) throws Exception {
        logger.info(code);
        Map<String, Object> map = new HashMap<>();
        Date now = new Date();
        WisdomUser sessionUser = (WisdomUser) session.getAttribute("currentUser");

        // 查询考勤码信息
        Example example = new Example(WisdomAttendanceCode.class);
        example.and().andEqualTo("code", code)
                .andEqualTo("attendanceTime", DateUtil.convertDate3(now));
        List<WisdomAttendanceCode> list = wisdomAttendanceCodeService.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            map.put("success", false);
            map.put("msg", "请输入今天需签到正确的考勤码！");
            return map;
        }

        WisdomAttendanceCode wisdomAttendanceCode = list.get(0);
        // 查询是否已签到
        Example example2 = new Example(WisdomAttendanceRecord.class);
        example2.and().andEqualTo("codeId", wisdomAttendanceCode.getCodeId())
                .andEqualTo("userId", sessionUser.getId());
        List<WisdomAttendanceRecord> records = wisdomAttendanceRecordService.selectByExample(example2);
        if (CollectionUtils.isNotEmpty(records)) {
            if (records.get(0).getAttendanceTime() != null) {
                map.put("success", false);
                map.put("msg", "您已签到，无需操作！");
                return map;
            }
        }

        String status = "";
        // 迟到
        if (now.getTime() > wisdomAttendanceCode.getExpireTime().getTime()) {
            status = "L";
        } else if (now.getTime() <= wisdomAttendanceCode.getExpireTime().getTime()) {
            status = "Y";
        }
        WisdomAttendanceRecord record = new WisdomAttendanceRecord();
        record.setCodeId(wisdomAttendanceCode.getCodeId());
        record.setUserId(sessionUser.getId());
        record.setStatus(status);
        record.setAttendanceTime(now);
        int i = wisdomAttendanceRecordService.saveNotNull(record);
        if (i > 0) {
            map.put("success", true);
            map.put("msg", "已签到！");
        } else {
            map.put("success", false);
            map.put("msg", "系统异常！");
        }
        return map;
    }

}
