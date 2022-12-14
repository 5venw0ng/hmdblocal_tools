package com.ruoyi.framework.shiro.realm;

import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.exception.user.*;
import com.ruoyi.framework.jwt.auth.JwtToken;
import com.ruoyi.framework.shiro.service.SysLoginService;
import com.ruoyi.framework.util.JWTUtil;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义Realm 处理登录 权限
 *
 * @author ruoyi
 */
public class UserRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysUserService userService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        SysUser user = ShiroUtils.getSysUser();
        // 角色列表
        Set<String> roles = new HashSet<String>();
        // 功能列表
        Set<String> menus = new HashSet<String>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            info.addRole("admin");
            info.addStringPermission("*:*:*");
        } else {
            roles = roleService.selectRoleKeys(user.getUserId());
            menus = menuService.selectPermsByUserId(user.getUserId());
            // 角色加入AuthorizationInfo认证对象
            info.setRoles(roles);
            // 权限加入AuthorizationInfo认证对象
            info.setStringPermissions(menus);
        }
        return info;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (authenticationToken instanceof JwtToken) {
            JwtToken jwtToken = (JwtToken) authenticationToken;
            String token = jwtToken.getToken();
            String username = JWTUtil.getUserName(token);
            if (username == null) {
                throw new AccountException("token 验证失败");
            }
            SysUser user = userService.selectUserByLoginName(username);
            if (user == null) {
                throw new AuthenticationException("用户数据不存在");
            }

            try {
                JWTUtil.verify(username, user.getPassword(), jwtToken.getToken());

                if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
                    throw new UserDeleteException();
                }

                if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
                    throw new UserBlockedException();
                }
            } catch (Exception e) {
                log.info("对用户[" + username + "]进行jwt登录验证..验证未通过{}", e.getMessage());
                throw new AuthenticationException(e.getMessage(), e);
            }
            return new SimpleAuthenticationInfo(user, null, getName());
        } else {
            UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
            String username = upToken.getUsername();
            String password = "";
            if (upToken.getPassword() != null) {
                password = new String(upToken.getPassword());
            }

            SysUser user = null;
            try {
                user = loginService.login(username, password);
            } catch (CaptchaException e) {
                throw new AuthenticationException(e.getMessage(), e);
            } catch (UserNotExistsException e) {
                throw new UnknownAccountException(e.getMessage(), e);
            } catch (UserPasswordNotMatchException e) {
                throw new IncorrectCredentialsException(e.getMessage(), e);
            } catch (UserPasswordRetryLimitExceedException e) {
                throw new ExcessiveAttemptsException(e.getMessage(), e);
            } catch (UserBlockedException e) {
                throw new LockedAccountException(e.getMessage(), e);
            } catch (RoleBlockedException e) {
                throw new LockedAccountException(e.getMessage(), e);
            } catch (Exception e) {
                log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
                throw new AuthenticationException(e.getMessage(), e);
            }
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
            return info;
        }
    }

    /**
     * 清理缓存权限
     */
    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
