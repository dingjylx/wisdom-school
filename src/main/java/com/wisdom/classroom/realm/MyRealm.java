package com.wisdom.classroom.realm;

import com.wisdom.classroom.entity.WisdomMenu;
import com.wisdom.classroom.entity.WisdomRole;
import com.wisdom.classroom.entity.WisdomUser;
import com.wisdom.classroom.mapper.WisdomMenuMapper;
import com.wisdom.classroom.mapper.WisdomRoleMapper;
import com.wisdom.classroom.service.WisdomUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory
            .getLogger(MyRealm.class);

    @Resource
    private WisdomUserService wisdomUserService;

    @Resource
    private WisdomRoleMapper troleMapper;

    @Resource
    private WisdomMenuMapper tmenuMapper;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String mobile = (String) SecurityUtils.getSubject().getPrincipal();

        //根据用户名查询出用户记录
        Example tuserExample = new Example(WisdomUser.class);
        tuserExample.or().andEqualTo("mobile", mobile);
        WisdomUser user = wisdomUserService.selectByExample(tuserExample).get(0);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        List<WisdomRole> roleList = troleMapper.selectRolesByUserId(user.getId());

        Set<String> roles = new HashSet<String>();
        if (roleList.size() > 0) {
            for (WisdomRole role : roleList) {
                roles.add(role.getName());
                //根据角色id查询所有资源
                List<WisdomMenu> menuList = tmenuMapper.selectMenusByRoleId(role.getId());
                for (WisdomMenu menu : menuList) {
                    info.addStringPermission(menu.getName()); // 添加权限
                }
            }
        }
        info.setRoles(roles);
        return info;
    }

    /**
     * 权限认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String mobile = (String) token.getPrincipal();
        Example tuserExample = new Example(WisdomUser.class);
        tuserExample.or().andEqualTo("mobile", mobile);
        List<WisdomUser> userList = wisdomUserService.selectByExample(tuserExample);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(userList.get(0).getMobile(), userList.get(0).getPassword(), "xxx");
            return authcInfo;
        }

    }


}
