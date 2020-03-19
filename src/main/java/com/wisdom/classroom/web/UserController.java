package com.wisdom.classroom.web;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.wisdom.classroom.entity.WisdomMenu;
import com.wisdom.classroom.entity.WisdomRole;
import com.wisdom.classroom.entity.WisdomUser;
import com.wisdom.classroom.entity.WisdomUserRole;
import com.wisdom.classroom.service.WisdomMenuService;
import com.wisdom.classroom.service.WisdomRoleService;
import com.wisdom.classroom.service.WisdomUserRoleService;
import com.wisdom.classroom.service.WisdomUserService;
import com.wisdom.classroom.vo.WisdomUserVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param
 * @Author: Zhaojiatao
 * @Description: 当前登录用户控制器
 * @Date: Created in 2018/2/8 19:28
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory
            .getLogger(UserController.class);

    @Resource
    private WisdomRoleService wisdomRoleService;

    @Resource
    private WisdomUserService wisdomUserService;

    @Resource
    private WisdomUserRoleService wisdomUserRoleService;

    @Resource
    private WisdomMenuService wisdomMenuService;

    /**
     * 用户登录请求
     *
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("/login")
    public Map<String, Object> login(@Valid WisdomUser user, BindingResult bindingResult, HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (bindingResult.hasErrors()) {
            map.put("success", false);
            map.put("errorInfo", bindingResult.getFieldError().getDefaultMessage());
            return map;
        }
        // 查询是否注册，若未注册，即默认注册为学生用户
        Example tuserExample = new Example(WisdomUser.class);
        tuserExample.and().andEqualTo("mobile", user.getMobile());
        int i = wisdomUserService.selectCountByExample(tuserExample);
        if (i == 0) {
            registerAndAuthStudentRole(user.getMobile(), user.getPassword());
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getMobile(), user.getPassword());
        try {
            subject.login(token); // 登录认证
            String mobile = (String) SecurityUtils.getSubject().getPrincipal();
            Example example = new Example(WisdomUser.class);
            example.or().andEqualTo("mobile", mobile);
            WisdomUser currentUser = wisdomUserService.selectByExample(example).get(0);
            session.setAttribute("currentUser", currentUser);
            List<WisdomRole> roleList = wisdomRoleService.selectRolesByUserId(currentUser.getId());
            map.put("roleList", roleList);
            map.put("roleSize", roleList.size());
            map.put("success", true);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("errorInfo", "用户名或者密码错误！");
            return map;
        }
    }

    private void registerAndAuthStudentRole(String mobile, String password) {
        // 插入用户表
        WisdomUser user = new WisdomUser();
        user.setMobile(mobile);
        user.setPassword(password);
        user.setUserType("S");// 学生
        int i = wisdomUserService.saveNotNull(user);
        logger.info("wisdomUserService.save user" + i);

        // 授权学生角色
        Example example = new Example(WisdomUser.class);
        example.or().andEqualTo("mobile", mobile);
        WisdomUser currentUser = wisdomUserService.selectByExample(example).get(0);

        WisdomUserRole userRole = new WisdomUserRole();
        userRole.setUserId(currentUser.getId());
        userRole.setRoleId(3);
        wisdomUserRoleService.saveNotNull(userRole);
        logger.info("注册并授权成功");
    }


    /**
     * 保存角色信息
     *
     * @param roleId
     * @param session
     * @return
     * @throws Exception
     */
    @ResponseBody
    @PostMapping("/saveRole")
    public Map<String, Object> saveRole(Integer roleId, HttpSession session) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        WisdomRole currentRole = wisdomRoleService.selectByKey(roleId);
        session.setAttribute("currentRole", currentRole); // 保存当前角色信息


        putTmenuOneClassListIntoSession(session);

        map.put("success", true);
        return map;
    }


    @ResponseBody
    @PostMapping("/query")
    public Map<String, Object> query(String mobile, HttpSession session) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        WisdomUser sessionUser = (WisdomUser) session.getAttribute("currentUser");
        WisdomUserVO currentUser = wisdomUserService.selectUserInfo(sessionUser.getId());
        map.put("currentUser", currentUser);
        map.put("success", true);
        return map;
    }

    @ResponseBody
    @PostMapping("/saveInfo")
    public Map<String, Object> saveInfo(WisdomUser user, HttpSession session) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        int i = wisdomUserService.updateNotNull(user);
        if (i == 1) {
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return map;
    }


    /**
     * 安全退出
     *
     * @return
     * @throws Exception
     */
    @GetMapping("/logout")
    public String logout() throws Exception {
//		logService.save(new Log(Log.LOGOUT_ACTION,"用户注销"));
        SecurityUtils.getSubject().logout();
        return "redirect:/tologin";
    }

    /**
     * 加载权限菜单
     *
     * @param session
     * @return
     * @throws Exception 这里传入的parentId是1
     */
    @ResponseBody
    @GetMapping("/loadMenuInfo")
    public String loadMenuInfo(HttpSession session) throws Exception {
        WisdomRole currentRole = (WisdomRole) session.getAttribute("currentRole");
        //根据当前用户的角色id查询所有菜单及子集json
        Integer parentId = wisdomMenuService.selectTopClassMenuId(currentRole.getId());
        String json = getAllMenuByParentId(parentId, currentRole.getId()).toString();
        //System.out.println(json);
        return json;
    }

    /**
     * 获取根频道所有菜单信息
     *
     * @param roleId
     * @return
     */
    private JsonArray getAllMenuByParentId(Integer parentId, Integer roleId) {
        JsonObject resultObject = new JsonObject();
        JsonArray jsonArray = this.getMenuByParentId(parentId, roleId);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(i);
            //判断该节点下时候还有子节点
            Example example = new Example(WisdomMenu.class);
            example.or().andEqualTo("pId", jsonObject.get("id").getAsString());
            if (wisdomMenuService.selectCountByExample(example) == 0) {
                continue;
            } else {
                jsonObject.add("children", getAllMenuByParentId(jsonObject.get("id").getAsInt(), roleId));
            }
        }
        return jsonArray;
    }

    /**
     * 根据父节点和用户角色id查询菜单
     *
     * @param parentId
     * @param roleId
     * @return
     */
    private JsonArray getMenuByParentId(Integer parentId, Integer roleId) {
        //List<Menu> menuList=menuService.findByParentIdAndRoleId(parentId, roleId);
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("pid", parentId);
        paraMap.put("roleid", roleId);
        List<WisdomMenu> menuList = wisdomMenuService.selectByParentIdAndRoleId(paraMap);
        JsonArray jsonArray = new JsonArray();
        for (WisdomMenu menu : menuList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", menu.getId()); // 节点id
            jsonObject.addProperty("title", menu.getName()); // 节点名称
            jsonObject.addProperty("spread", false); // 不展开
            jsonObject.addProperty("icon", menu.getIcon());
            if (StringUtils.isNotEmpty(menu.getUrl())) {
                jsonObject.addProperty("href", menu.getUrl()); // 菜单请求地址
            }
            jsonArray.add(jsonObject);

        }
        return jsonArray;
    }


    public void putTmenuOneClassListIntoSession(HttpSession session) {
        int parentId = -1;
        WisdomUser sessionUser = (WisdomUser) session.getAttribute("currentUser");
        if (sessionUser.getUserType().equals("ADMIN")) {
            parentId = -1;
        } else {
            parentId = 1;
        }
        Example example = new Example(WisdomMenu.class);
        example.or().andEqualTo("pId", parentId);
        List<WisdomMenu> tmenuOneClassList = wisdomMenuService.selectByExample(example);
        session.setAttribute("tmenuOneClassList", tmenuOneClassList);
    }


}
