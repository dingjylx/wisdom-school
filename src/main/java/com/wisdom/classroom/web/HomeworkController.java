package com.wisdom.classroom.web;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wisdom.classroom.entity.*;
import com.wisdom.classroom.service.*;
import com.wisdom.classroom.util.ControllerReturnApi;
import com.wisdom.classroom.util.FileUtil;
import com.wisdom.classroom.util.PageResult;
import com.wisdom.classroom.vo.HomeworkAnswerVO;
import com.wisdom.classroom.vo.HomeworkVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/homework")
public class HomeworkController {

    private static final Logger logger = LoggerFactory
            .getLogger(HomeworkController.class);

    @Value("${upload.url}")
    private String uploadUrl;

    @Resource
    private WisdomHomeworkService wisdomHomeworkService;

    @Resource
    private WisdomHomeworkAnswerService wisdomHomeworkAnswerService;

    @Resource
    private WisdomFileService wisdomFileService;

    @RequestMapping("/main")
    @RequiresPermissions(value = {"作业管理"})
    public String main(Model model, HttpSession session) {
        model.addAttribute("sessionUser", (WisdomUser) session.getAttribute("currentUser"));
        return "homework/main";
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public PageResult list(Integer pageNum, Integer pageSize, String name) {
        PageHelper.startPage(pageNum, pageSize);
        List<HomeworkVO> list = wisdomHomeworkService.selectHomeworkList(name);
        //将查询到的数据封装到PageInfo对象
        PageInfo<HomeworkVO> pageInfo = new PageInfo(list);
        PageResult pageResult = new PageResult();
        pageResult.setCode(0);
        pageResult.setMsg("");
        pageResult.setData(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    @ResponseBody
    @PostMapping("/addHomework")
    public Map<String, Object> addHomework(WisdomHomework wisdomHomework, HttpSession session) throws Exception {
        logger.info(JSONObject.toJSONString(wisdomHomework));
        Map<String, Object> map = new HashMap<>();
        //插入作业记录
        WisdomUser sessionUser = (WisdomUser) session.getAttribute("currentUser");
        wisdomHomework.setUserId(sessionUser.getId());
        int i = wisdomHomeworkService.saveNotNull(wisdomHomework);
        if (i == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

    @ResponseBody
    @PostMapping("/update")
    public Map<String, Object> update(WisdomHomework wisdomHomework) throws Exception {
        logger.info(JSONObject.toJSONString(wisdomHomework));
        Map<String, Object> map = new HashMap<>();
        // 更新作业的fileId
        int i = wisdomHomeworkService.updateNotNull(wisdomHomework);
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
        Example example = new Example(WisdomHomework.class);
        example.and().andEqualTo("id", id);
        int i = wisdomHomeworkService.deleteByExample(example);
        if (i == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

    @ResponseBody
    @PostMapping("/addAnswer")
    public Map<String, Object> addAnswer(WisdomHomeworkAnswer wisdomHomeworkAnswer, HttpSession session) throws Exception {
        logger.info(JSONObject.toJSONString(wisdomHomeworkAnswer));
        Map<String, Object> map = new HashMap<>();
        //插入作业记录
        WisdomUser sessionUser = (WisdomUser) session.getAttribute("currentUser");
        wisdomHomeworkAnswer.setUserId(sessionUser.getId());
        int i = wisdomHomeworkAnswerService.saveNotNull(wisdomHomeworkAnswer);
        if (i == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/queryAnswers")
    public PageResult queryClassStudents(Integer pageNum, Integer pageSize, Long homeworkId) {
        PageHelper.startPage(pageNum, pageSize);
        List<HomeworkAnswerVO> list = wisdomHomeworkService.selectHomeworkAnswerList(homeworkId);
        //将查询到的数据封装到PageInfo对象
        PageInfo<HomeworkAnswerVO> pageInfo = new PageInfo(list);
        PageResult pageResult = new PageResult();
        pageResult.setCode(0);
        pageResult.setMsg("");
        pageResult.setData(pageInfo.getList());
        pageResult.setTotal(pageInfo.getTotal());
        return pageResult;
    }

    /**
     * 上传附件
     *
     * @param request
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadOne(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        Map map = new HashMap();
        try {
            //上传目录地址
            String uploadDir = uploadUrl;
            //如果目录不存在，自动创建文件夹
            File dir = new File(uploadUrl);
            if (!dir.exists()) {
                dir.mkdir();
            }
            //调用上传方法
            String fileName = FileUtil.executeUpload(uploadDir, file);
            uploadDir = uploadDir.substring(0, uploadDir.length() - 1);

            //插入附件表
            WisdomFile wisdomFile = new WisdomFile();
            wisdomFile.setFileName(fileName);
            wisdomFile.setFileSize(file.getSize());
            wisdomFile.setFileUrl(uploadDir);
            wisdomFileService.saveNotNull(wisdomFile);

            map.put("fileId", wisdomFile.getFileId());
            map.put("fileName", fileName);
            map.put("dir", uploadDir);
        } catch (Exception e) {
            //打印错误堆栈信息
            e.printStackTrace();
            return ControllerReturnApi.returnJson(2, "上传失败", map);
        }

        return ControllerReturnApi.returnJson(1, "上传成功", map);
    }

    //下载
    @RequestMapping(value = "/download")
    @ResponseBody
    public Map<String, Object> downloadOne(HttpServletRequest req, HttpServletResponse response) throws IOException {
        String fileName = req.getParameter("fileName");// 设置文件名，根据业务需要替换成要下载的文件名
        String downloadDir = uploadUrl;//下载目录
        String realPath = downloadDir + fileName;//
        File file = new File(realPath);//下载目录加文件名拼接成realpath
        if (file.exists()) { //判断文件父目录是否存在
            // 解决浏览器下载文件中文乱码
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("----------file download" + fileName);
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return ControllerReturnApi.returnJson(2, "fail：" + realPath + fileName);
            }
        }
        return ControllerReturnApi.returnJson(1, "下载成功：" + realPath + fileName);
    }


}
